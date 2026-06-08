const apiBase = ""
const submitButton = document.getElementById("submitButton");
const { AdvancedMarkerElement, PinElement } = await google.maps.importLibrary("marker");


const form = document.getElementById("loginForm");
form.addEventListener("submit", login)


async function getMockBuildings() {

  try {
    const res = await fetch("./buildings.json");
    const data = await res.json();


    showBuildingData(data);
  } catch (err) {
    console.log("error", err)
  }
}


async function login(e) {
  e.preventDefault();
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  if (!username || !password) {
    console.log("input error")
    return;
  }
  try {
    submitButton.ariaBusy = true;
    const res = await fetch(apiBase + "/login", {
      method: 'POST',
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username: username, password: password })
    });
    if (!res.ok) {
      console.log("Error", res.status)
    }
    console.log("getting buildings");

    const data = await res.json();

    console.log("res3", data);
    buildingsList = data.buildings;
    if (buildingsList.length < 1) {
        window.alert("Du har ingen opskrivninger")
    }
    showBuildingData(data.buildings);

  } catch (err) {
    console.log(err);
    window.alert("Forkert email/kodeord")
  } finally {
    submitButton.ariaBusy = false;
  }
}
let buildingsList = [];

function showBuildingData(buildings) {
  initMap(buildings);
  const div = document.getElementById("buildingList");
  div.style.marginTop = "50px"
  div.classList.add("pico")
  div.classList.add("container")

  buildings.forEach((building) => {
    const h3 = document.createElement("h3");
    const p = document.createElement("p");
    const p1 = document.createElement("p");
    const p2 = document.createElement("p");
    const p3 = document.createElement("p");
    const p4 = document.createElement("p");
    const p5 = document.createElement("p");

    const a = document.createElement('a');
    a.innerHTML = building.name;
    a.href = building.linkToBuilding;
    h3.appendChild(a);


    const a1 = document.createElement('a');
    a1.innerHTML = building.desc_address;

    a1.href = building.googleMapsUrl;

    p1.appendChild(a1);

    p3.innerHTML = "A: " + building.ranking.a;
    p4.innerHTML = "B: " + building.ranking.b;
    p5.innerHTML = "C: " + building.ranking.b;

    h3.classList.add('pico')
    p1.classList.add('pico')
    p2.classList.add('pico')
    p3.classList.add('pico')
    p4.classList.add('pico')
    p5.classList.add('pico')

    div.appendChild(h3);
    div.appendChild(p);
    div.appendChild(p1);
    div.appendChild(p2);
    div.appendChild(p3);
    div.appendChild(p4);
    div.appendChild(p5);

  });
}

let map;

async function initMap(buildings) {
  // The location of Uluru

  console.log(buildings[0]);

  const position = { lat: parseFloat(buildings[0].latitude), lng: parseFloat(buildings[0].longitude) };
  // Request needed libraries.
  //@ts-ignore
  const { Map } = await google.maps.importLibrary("maps");
  const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");

  // The map, centered at Uluru
  map = new Map(document.getElementById("map"), {
    zoom: 4,
    center: position,
    mapId: "s-dk-map",
    zoom: 11,
  });

  // Array of marker data
  const locations = [];

  buildings.forEach((b) => {
    let contentString = `<div><strong> <h1>${b.name}</h1> </string> <strong>A:</strong> ${b.ranking.a} </br> B: ${b.ranking.b} </br> C: ${b.ranking.c}</div>`
    let ranking = 'C'
    if (b.ranking.b > 0) ranking = 'B'
    if (b.ranking.a > 0) ranking = 'A';
    locations.push({ position: { lat: parseFloat(b.latitude), lng: parseFloat(b.longitude) }, title: b.name, data: contentString, ranking: ranking })
  })
  // Add each marker
  locations.forEach(loc => {

    const infowindow = new google.maps.InfoWindow({
      content: loc.data,
      ariaLabel: loc.title,
    });

    let color = "red";
    if (loc.ranking == 'A')  {
      color = "green"
    } else if (loc.ranking == 'B') {
      color = "orange"
    }
    const pinEl = document.createElement('div');
    pinEl.style.display = 'flex';
    pinEl.style.alignItems = 'center';
    pinEl.style.justifyContent = 'center';
    pinEl.style.width = '28px';
    pinEl.style.height = '28px';
    pinEl.style.borderRadius = '50%';
    pinEl.style.background = color;
    pinEl.style.color = 'white';
    pinEl.style.boxShadow = '0 2px 6px rgba(0,0,0,0.35)';
    pinEl.style.border = '1px solid rgba(0, 0, 0, 0.6)';
    pinEl.textContent = loc.ranking; // A / B / C


    const marker = new google.maps.marker.AdvancedMarkerElement({
      map: map,
      position: loc.position,
      content: pinEl,
    });


    marker.addEventListener("mouseover", () => {
      infowindow.open({
        anchor: marker,
        map,
      });
    });
    marker.addEventListener("click", () => {
      infowindow.open({
        anchor: marker,
        map,
      });
    });
    marker.addEventListener("mouseout", () => {
      infowindow.close();
    });
  });

}

