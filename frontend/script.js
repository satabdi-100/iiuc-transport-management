const BASE_URL = 'http://localhost:8080/api';


const ADMIN_PASSWORD = 'iiuc123';
let isAdmin = false;

function loginAsAdmin() {
  const input = document.getElementById('admin-password-input').value;
  if (input === ADMIN_PASSWORD) {
    isAdmin = true;
    document.getElementById('login-screen').style.display = 'none';
    document.getElementById('login-error').style.display = 'none';
    setAdminMode();
    init();
  } else {
    document.getElementById('login-error').style.display = 'block';
  }
}

function loginAsUser() {
  isAdmin = false;
  document.getElementById('login-screen').style.display = 'none';
  setUserMode();
  init();
}

function setAdminMode() {
  document.querySelectorAll('.btn-primary').forEach(btn => btn.style.display = 'inline-block');
  const title = document.querySelector('.site-title');
  if (title && !document.getElementById('admin-badge')) {
    const badge = document.createElement('span');
    badge.id = 'admin-badge';
    badge.style = 'background:#2563eb; color:#fff; font-size:12px; padding:2px 8px; border-radius:10px; margin-left:8px;';
    badge.textContent = 'ADMIN';
    title.appendChild(badge);
  }
}

function setUserMode() {
  document.querySelectorAll('.btn-primary').forEach(btn => btn.style.display = 'none');
}


document.querySelectorAll('.tab-btn').forEach(btn => {
  btn.addEventListener('click', () => {
    document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
    document.querySelectorAll('.tab-section').forEach(s => s.classList.remove('active'));
    btn.classList.add('active');
    document.getElementById('tab-' + btn.dataset.tab).classList.add('active');
  });
});


function openModal(id) { document.getElementById(id).style.display = 'flex'; }
function closeModal(id) { document.getElementById(id).style.display = 'none'; }

document.querySelectorAll('.modal-overlay').forEach(overlay => {
  overlay.addEventListener('click', (e) => {
    if (e.target === overlay) overlay.style.display = 'none';
  });
});


function showToast(msg, isError = false) {
  const toast = document.getElementById('toast');
  toast.textContent = msg;
  toast.className = 'toast' + (isError ? ' error' : '');
  toast.classList.add('show');
  setTimeout(() => toast.classList.remove('show'), 3000);
}


async function updateStats() {
  try {
    const [busRes, routeRes, schedRes, driverRes] = await Promise.all([
      fetch(`${BASE_URL}/buses`), fetch(`${BASE_URL}/routes`),
      fetch(`${BASE_URL}/schedules`), fetch(`${BASE_URL}/drivers`)
    ]);
    const buses = await busRes.json();
    const routes = await routeRes.json();
    const schedules = await schedRes.json();
    const drivers = await driverRes.json();

    document.getElementById('stat-buses').textContent = buses.length;
    document.getElementById('stat-routes').textContent = routes.length;
    document.getElementById('stat-schedules').textContent = schedules.length;
    document.getElementById('stat-drivers').textContent = drivers.length;


    const busesTbody = document.getElementById('dash-buses-tbody');
    busesTbody.innerHTML = buses.length === 0
      ? '<tr><td colspan="2" class="empty-row">No buses added yet</td></tr>'
      : buses.slice(-5).reverse().map(b =>
        `<tr><td>${b.busNumber}</td><td>${b.capacity}</td></tr>`
      ).join('');


    const schedTbody = document.getElementById('dash-schedules-tbody');
    schedTbody.innerHTML = schedules.length === 0
      ? '<tr><td colspan="3" class="empty-row">No schedules added yet</td></tr>'
      : schedules.slice(-5).reverse().map(s =>
        `<tr><td>${s.routeName}</td><td>${s.schedule}</td><td>${s.driverName}</td></tr>`
      ).join('');
  } catch (err) { console.error('Stats error:', err); }
}


async function saveBus() {
  const editId = document.getElementById('bus-edit-id').value;
  const busNumber = document.getElementById('bus-number').value.trim();
  const capacity = parseInt(document.getElementById('bus-capacity').value);

  if (!busNumber || !capacity) {
    showToast('Please fill in all fields.', true); return;
  }

  const body = JSON.stringify({ busNumber, capacity });

  try {
    if (editId) {
      await fetch(`${BASE_URL}/buses/${editId}`, {
        method: 'PUT', headers: { 'Content-Type': 'application/json' }, body
      });
      showToast('Bus updated successfully!');
    } else {
      await fetch(`${BASE_URL}/buses`, {
        method: 'POST', headers: { 'Content-Type': 'application/json' }, body
      });
      showToast('Bus added successfully!');
    }
    closeModal('bus-modal'); clearBusForm(); loadBuses(); updateStats();
  } catch (err) { showToast('Error saving bus. Is the server running?', true); }
}

async function loadBuses() {
  try {
    const res = await fetch(`${BASE_URL}/buses`);
    renderBuses(await res.json());
  } catch {
    document.getElementById('buses-tbody').innerHTML =
      '<tr><td colspan="4" class="empty-row">⚠️ Cannot connect to server</td></tr>';
  }
}

function editBus(id, busNumber, capacity) {
  document.getElementById('bus-modal-title').textContent = 'Edit Bus';
  document.getElementById('bus-edit-id').value = id;
  document.getElementById('bus-number').value = busNumber;
  document.getElementById('bus-capacity').value = capacity;
  openModal('bus-modal');
}

async function deleteBus(id) {
  if (!confirm('Delete this bus?')) return;
  try {
    await fetch(`${BASE_URL}/buses/${id}`, { method: 'DELETE' });
    showToast('Bus deleted.'); loadBuses(); updateStats();
  } catch { showToast('Error deleting bus.', true); }
}

function clearBusForm() {
  document.getElementById('bus-modal-title').textContent = 'Add New Bus';
  ['bus-edit-id', 'bus-number', 'bus-capacity'].forEach(id =>
    document.getElementById(id).value = '');
}

function renderBuses(buses) {
  const tbody = document.getElementById('buses-tbody');
  if (!buses || buses.length === 0) {
    tbody.innerHTML = '<tr><td colspan="4" class="empty-row">No buses added yet</td></tr>';
    return;
  }
  tbody.innerHTML = buses.map(b => `
    <tr>
      <td>${b.id}</td>
      <td>${b.busNumber}</td>
      <td>${b.capacity}</td>
      <td>
        ${isAdmin
      ? `<button class="btn-edit" onclick="editBus(${b.id},'${b.busNumber}',${b.capacity})">Edit</button>
             <button class="btn-delete" onclick="deleteBus(${b.id})">Delete</button>`
      : '—'}
      </td>
    </tr>`).join('');
}

document.querySelector('[onclick="openModal(\'bus-modal\')"]')
  .addEventListener('click', clearBusForm);


async function saveRoute() {
  const editId = document.getElementById('route-edit-id').value;
  const routeName = document.getElementById('route-name').value.trim();
  const busNumber = document.getElementById('route-bus').value.trim();
  if (!routeName || !busNumber) { showToast('Please fill in all fields.', true); return; }
  const body = JSON.stringify({ routeName, busNumber });
  try {
    if (editId) {
      await fetch(`${BASE_URL}/routes/${editId}`, {
        method: 'PUT', headers: { 'Content-Type': 'application/json' }, body
      });
      showToast('Route updated successfully!');
    } else {
      await fetch(`${BASE_URL}/routes`, {
        method: 'POST', headers: { 'Content-Type': 'application/json' }, body
      });
      showToast('Route added successfully!');
    }
    closeModal('route-modal'); clearRouteForm(); loadRoutes(); updateStats();
  } catch { showToast('Error saving route.', true); }
}

async function loadRoutes() {
  try {
    const res = await fetch(`${BASE_URL}/routes`);
    renderRoutes(await res.json());
  } catch {
    document.getElementById('routes-tbody').innerHTML =
      '<tr><td colspan="4" class="empty-row">⚠️ Cannot connect to server</td></tr>';
  }
}

function editRoute(id, routeName, busNumber) {
  document.getElementById('route-modal-title').textContent = 'Edit Route';
  document.getElementById('route-edit-id').value = id;
  document.getElementById('route-name').value = routeName;
  document.getElementById('route-bus').value = busNumber;
  openModal('route-modal');
}

async function deleteRoute(id) {
  if (!confirm('Delete this route?')) return;
  try {
    await fetch(`${BASE_URL}/routes/${id}`, { method: 'DELETE' });
    showToast('Route deleted.'); loadRoutes(); updateStats();
  } catch { showToast('Error deleting route.', true); }
}

function clearRouteForm() {
  document.getElementById('route-modal-title').textContent = 'Add New Route';
  ['route-edit-id', 'route-name', 'route-bus'].forEach(id =>
    document.getElementById(id).value = '');
}

function renderRoutes(routes) {
  const tbody = document.getElementById('routes-tbody');
  if (!routes || routes.length === 0) {
    tbody.innerHTML = '<tr><td colspan="4" class="empty-row">No routes added yet</td></tr>';
    return;
  }
  tbody.innerHTML = routes.map(r => `
    <tr>
      <td>${r.id}</td>
      <td>${r.routeName}</td>
      <td>${r.busNumber}</td>
      <td>
        ${isAdmin
      ? `<button class="btn-edit" onclick="editRoute(${r.id},'${r.routeName}','${r.busNumber}')">Edit</button>
             <button class="btn-delete" onclick="deleteRoute(${r.id})">Delete</button>`
      : '—'}
      </td>
    </tr>`).join('');
}

document.querySelector('[onclick="openModal(\'route-modal\')"]')
  .addEventListener('click', clearRouteForm);


async function saveSchedule() {
  const editId = document.getElementById('schedule-edit-id').value;
  const routeName = document.getElementById('schedule-route').value.trim();
  const schedule = document.getElementById('schedule-time').value.trim();
  const driverName = document.getElementById('schedule-driver').value.trim();
  const busNumber = document.getElementById('schedule-bus').value.trim();
  if (!routeName || !schedule || !driverName || !busNumber) {
    showToast('Please fill in all fields.', true); return;
  }
  const body = JSON.stringify({ routeName, schedule, driverName, busNumber });
  try {
    if (editId) {
      await fetch(`${BASE_URL}/schedules/${editId}`, {
        method: 'PUT', headers: { 'Content-Type': 'application/json' }, body
      });
      showToast('Schedule updated successfully!');
    } else {
      await fetch(`${BASE_URL}/schedules`, {
        method: 'POST', headers: { 'Content-Type': 'application/json' }, body
      });
      showToast('Schedule added successfully!');
    }
    closeModal('schedule-modal'); clearScheduleForm(); loadSchedules(); updateStats();
  } catch { showToast('Error saving schedule.', true); }
}

async function loadSchedules() {
  try {
    const res = await fetch(`${BASE_URL}/schedules`);
    renderSchedules(await res.json());
  } catch {
    document.getElementById('schedules-tbody').innerHTML =
      '<tr><td colspan="6" class="empty-row">⚠️ Cannot connect to server</td></tr>';
  }
}

function editSchedule(id, routeName, schedule, driverName, busNumber) {
  document.getElementById('schedule-modal-title').textContent = 'Edit Schedule';
  document.getElementById('schedule-edit-id').value = id;
  document.getElementById('schedule-route').value = routeName;
  document.getElementById('schedule-time').value = schedule;
  document.getElementById('schedule-driver').value = driverName;
  document.getElementById('schedule-bus').value = busNumber;
  openModal('schedule-modal');
}

async function deleteSchedule(id) {
  if (!confirm('Delete this schedule?')) return;
  try {
    await fetch(`${BASE_URL}/schedules/${id}`, { method: 'DELETE' });
    showToast('Schedule deleted.'); loadSchedules(); updateStats();
  } catch { showToast('Error deleting schedule.', true); }
}

function clearScheduleForm() {
  document.getElementById('schedule-modal-title').textContent = 'Add New Schedule';
  ['schedule-edit-id', 'schedule-route', 'schedule-time',
    'schedule-driver', 'schedule-bus'].forEach(id =>
      document.getElementById(id).value = '');
}

function renderSchedules(schedules) {
  const tbody = document.getElementById('schedules-tbody');
  if (!schedules || schedules.length === 0) {
    tbody.innerHTML = '<tr><td colspan="6" class="empty-row">No schedules added yet</td></tr>';
    return;
  }
  tbody.innerHTML = schedules.map(s => `
    <tr>
      <td>${s.id}</td>
      <td>${s.routeName}</td>
      <td>${s.schedule}</td>
      <td>${s.driverName}</td>
      <td>${s.busNumber}</td>
      <td>
        ${isAdmin
      ? `<button class="btn-edit" onclick="editSchedule(${s.id},'${s.routeName}','${s.schedule}','${s.driverName}','${s.busNumber}')">Edit</button>
             <button class="btn-delete" onclick="deleteSchedule(${s.id})">Delete</button>`
      : '—'}
      </td>
    </tr>`).join('');
}

document.querySelector('[onclick="openModal(\'schedule-modal\')"]')
  .addEventListener('click', clearScheduleForm);


async function saveDriver() {
  const editId = document.getElementById('driver-edit-id').value;
  const driverName = document.getElementById('driver-name').value.trim();
  const busNumber = document.getElementById('driver-bus').value.trim();
  const mobileNumber = document.getElementById('driver-mobile').value.trim();
  if (!driverName || !busNumber || !mobileNumber) { showToast('Please fill in all fields.', true); return; }
  const body = JSON.stringify({ driverName, busNumber, mobileNumber });
  try {
    if (editId) {
      await fetch(`${BASE_URL}/drivers/${editId}`, {
        method: 'PUT', headers: { 'Content-Type': 'application/json' }, body
      });
      showToast('Driver updated successfully!');
    } else {
      await fetch(`${BASE_URL}/drivers`, {
        method: 'POST', headers: { 'Content-Type': 'application/json' }, body
      });
      showToast('Driver added successfully!');
    }
    closeModal('driver-modal'); clearDriverForm(); loadDrivers(); updateStats();
  } catch { showToast('Error saving driver.', true); }
}

async function loadDrivers() {
  try {
    const res = await fetch(`${BASE_URL}/drivers`);
    renderDrivers(await res.json());
  } catch {
    document.getElementById('drivers-tbody').innerHTML =
      '<tr><td colspan="4" class="empty-row">⚠️ Cannot connect to server</td></tr>';
  }
}

function editDriver(id, driverName, busNumber, mobileNumber) {
  document.getElementById('driver-modal-title').textContent = 'Edit Driver';
  document.getElementById('driver-edit-id').value = id;
  document.getElementById('driver-name').value = driverName;
  document.getElementById('driver-mobile').value = mobileNumber || '';
  document.getElementById('driver-bus').value = busNumber;
  openModal('driver-modal');
}

async function deleteDriver(id) {
  if (!confirm('Delete this driver?')) return;
  try {
    await fetch(`${BASE_URL}/drivers/${id}`, { method: 'DELETE' });
    showToast('Driver deleted.'); loadDrivers(); updateStats();
  } catch { showToast('Error deleting driver.', true); }
}

function clearDriverForm() {
  document.getElementById('driver-modal-title').textContent = 'Add New Driver';
  ['driver-edit-id', 'driver-name', 'driver-bus', 'driver-mobile'].forEach(id =>
    document.getElementById(id).value = '');
}

function renderDrivers(drivers) {
  const tbody = document.getElementById('drivers-tbody');
  if (!drivers || drivers.length === 0) {
    tbody.innerHTML = '<tr><td colspan="5" class="empty-row">No drivers added yet</td></tr>';
    return;
  }
  tbody.innerHTML = drivers.map(d => `
    <tr>
      <td>${d.id}</td>
      <td>${d.driverName}</td>
      <td>${d.busNumber}</td>
      <td>${d.mobileNumber || '—'}</td>
      <td>
        ${isAdmin
        ? `<button class="btn-edit" onclick="editDriver(${d.id},'${d.driverName}','${d.busNumber}','${d.mobileNumber || ''}')">Edit</button>
           <button class="btn-delete" onclick="deleteDriver(${d.id})">Delete</button>`
        : '—'}
      </td>
    </tr>`).join('');
}

document.querySelector('[onclick="openModal(\'driver-modal\')"]')
  .addEventListener('click', clearDriverForm);


function init() {
  loadBuses(); loadRoutes(); loadSchedules(); loadDrivers(); updateStats();
}