// Función para manejar el inicio de sesión
function handleLogin() {
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  console.log('Intentando iniciar sesión con:', username);

  fetch('http://192.168.100.3:8081/api/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ username, password }),
    credentials: 'include'
  })
  .then(response => {
    if (response.ok) {
      console.log('Inicio de sesión exitoso');
      window.location.href = '/users';
    } else {
      throw new Error('Credenciales inválidas');
    }
  })
  .catch(error => {
    console.error('Error de inicio de sesión:', error);
    alert('Credenciales inválidas');
  });
}

// Función para obtener y mostrar los usuarios en la tabla
function getUsers() {
  const statusMessage = document.getElementById('status-message'); // Elemento para mensajes

  fetch('http://192.168.100.3:8081/api/users', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    },
    credentials: 'include'
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('No hay usuarios en la lista. Reintentando...');
    }
    return response.json();
  })
  .then(data => {
    statusMessage.textContent = "Lista de usuarios recuperada exitosamente.";
    const userListBody = document.querySelector('#user-list tbody');
    userListBody.innerHTML = '';

    data.forEach(user => {
      const row = document.createElement('tr');

      // Nombre
      const nameCell = document.createElement('td');
      nameCell.textContent = user.name;
      row.appendChild(nameCell);

      // Email
      const emailCell = document.createElement('td');
      emailCell.textContent = user.email;
      row.appendChild(emailCell);

      // Usuario
      const usernameCell = document.createElement('td');
      usernameCell.textContent = user.username;
      row.appendChild(usernameCell);

      // Acciones
      const actionsCell = document.createElement('td');

      // Enlace de editar
      const editLink = document.createElement('button');
      editLink.textContent = 'Editar';
      editLink.className = 'btn btn-primary mr-2';
      editLink.addEventListener('click', () => {
        editUser(user.id, user.name, user.email, user.username);
      });
      actionsCell.appendChild(editLink);

      // Enlace de eliminar
      const deleteLink = document.createElement('button');
      deleteLink.textContent = 'Eliminar';
      deleteLink.className = 'btn btn-danger';
      deleteLink.addEventListener('click', () => {
        deleteUser(user.id);
      });
      actionsCell.appendChild(deleteLink);

      row.appendChild(actionsCell);
      userListBody.appendChild(row);
    });
  })
  .catch(error => {
    console.error('Error:', error);
    statusMessage.textContent = error.message; // Mostrar mensaje de reintento
  });
}

// Llamar a getUsers() cuando se cargue la página de usuarios
if (window.location.pathname === '/users') {
  getUsers();
}

// Función para crear un nuevo usuario
function createUser() {
  const data = {
    name: document.getElementById('name').value,
    email: document.getElementById('email').value,
    username: document.getElementById('username').value,
    password: document.getElementById('password').value
  };

  fetch('http://192.168.100.3:8081/api/users', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  })
  .then(response => response.json())
  .then(data => {
    alert('Usuario creado exitosamente.');
    getUsers();
  })
  .catch(error => {
    console.error('Error:', error);
    alert('No se pudo crear el usuario.');
  });
}

// Función para cargar los datos del usuario en el formulario de edición
function editUser(id, name, email, username) {
  document.getElementById('edit-user-id').value = id;
  document.getElementById('edit-name').value = name;
  document.getElementById('edit-email').value = email;
  document.getElementById('edit-username').value = username;

  // Mostrar el formulario de edición
  document.getElementById('edit-user-form').style.display = 'block';
}

// Función para actualizar un usuario
function updateUser() {
  const userId = document.getElementById('edit-user-id').value;
  const data = {
    name: document.getElementById('edit-name').value,
    email: document.getElementById('edit-email').value,
    username: document.getElementById('edit-username').value,
    password: document.getElementById('edit-password').value
  };

  fetch(`http://192.168.100.3:8081/api/users/${userId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data),
    credentials: 'include'
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('No se pudo actualizar el usuario.');
    }
    return response.json();
  })
  .then(data => {
    console.log('Usuario actualizado:', data);
    getUsers();
    document.getElementById('edit-user-form').style.display = 'none';
  })
  .catch(error => {
    console.error('Error:', error);
    alert('No se pudo actualizar el usuario.');
  });
}

// Función para eliminar un usuario
function deleteUser(userId) {
  if (confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
    fetch(`http://192.168.100.3:8081/api/users/${userId}`, {
      method: 'DELETE',
      credentials: 'include'
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('No se pudo eliminar el usuario.');
      }
      alert('Usuario eliminado exitosamente.');
      getUsers();
    })
    .catch(error => {
      console.error('Error:', error);
      alert('No se pudo eliminar el usuario.');
    });
  }
}

// Cargar la lista de usuarios al cargar la página de usuarios
if (window.location.pathname === '/users') {
  getUsers();
}

