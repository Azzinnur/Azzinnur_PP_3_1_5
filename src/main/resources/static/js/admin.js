$(document).ready(async () => {
    await adminDataFilling()
    await getTableWithUsers()
    let rolesForSelection = await getRolesForSelection()
    document.getElementById('rolesSelect').innerHTML = rolesForSelection
    document.getElementById('rolesDelete').innerHTML = rolesForSelection
    document.getElementById('editRoles').innerHTML = rolesForSelection
    addUser()
    deleteUser()
    editUser()
    clearModal()
})

//csrf токен
const csrfToken = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');

//Заполнение строки в хэдере и таблицы User
async function adminDataFilling() {
    let admin = await fetch('http://localhost:8080/api/admin/currentUser')
        .then(result => result.json())
    let adminRoles = getRoles(admin)
    document.querySelector(`#header-mail`).textContent = `${admin.email}`
    document.querySelector(`#header-roles`).textContent = `${adminRoles}`
    let table = document.querySelector('#admin-table')
    let tableFilling = `<tr>
                            <td>${admin.id}</td>
                            <td>${admin.firstName}</td>
                            <td>${admin.lastName}</td>
                            <td>${admin.age}</td>
                            <td>${admin.email}</td>
                            <td>${adminRoles}</td>
                        </tr>`
    table.innerHTML = tableFilling
}

//Заполнение таблицы со всеми юзерами
async function getTableWithUsers() {
    let table = $('#all-users-table');
    table.empty();

    await fetch('http://localhost:8080/api/admin/', {method: 'GET'})
        .then(response => response.json())
        .then(users => {
            users.forEach(user => {
                let userRoles = getRoles(user)
                let tableFilling = `$(
                                            <tr>
                                                <td>${user.id}</td>
                                                <td>${user.firstName}</td>
                                                <td>${user.lastName}</td>
                                                <td>${user.age}</td>
                                                <td>${user.email}</td>
                                                <td>${userRoles}</td>
                                                <td>
                                                    <button type="button" class="btn btn-info" data-toggle="modal"
                                                        data-target="#editUserModal" onclick="editModalData(${user.id})">
                                                        Edit
                                                    </button>
                                                </td>
                                                <td>
                                                    <button type="button" class="btn btn-danger" data-toggle="modal"
                                                       data-target="#deleteUserModal" onclick="deleteModalData(${user.id})">                                                     
                                                       Delete
                                                    </button>
                                                </td>
                                            </tr>                                  
                )`;
                table.append(tableFilling)
            })
        })
}

//Извлечение всех ролей и преобразование их в строку
function getRoles(user) {
    let roleNames = ''
    user.roles.forEach(role => {
        let roleName = role.name.split('_')[1]
        roleNames += `${roleName} `
    })
    return roleNames
}

//Создание списка ролей для окна мультивыбора
async function getRolesForSelection() {
    let roleOptions = ''
    await fetch('http://localhost:8080/api/admin/roles').then(result => result.json())
        .then(roles => roles.forEach(role => {
                let roleName = role.name.split('_')[1]
                let roleOption = `<option value="${role.id}">${roleName}</option>`
                roleOptions += roleOption.trim()
            })
        )
    return roleOptions
}

//Создание массива с ролями для JSON
function setRoles(userForm) {
    let userRoles = [];
    for (let i = 0; i < userForm.roles.options.length; i++) {
        if (userForm.roles.options[i].selected) userRoles.push({
            id: userForm.roles.options[i].value,
            role: "ROLE_" + userForm.roles.options[i].text,
            authority: "ROLE_" + userForm.roles.options[i].text
        });
    }
    return userRoles;
}

//Добавление пользователя
function addUser() {
    let newUserForm = document.forms["new-user-form"];
    newUserForm.addEventListener("submit", async ev => {
        ev.preventDefault();
        let newUserRoles = setRoles(newUserForm)
        let response = await fetch("http://localhost:8080/api/admin/", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': csrfToken
            },
            body: JSON.stringify({
                firstName: newUserForm.firstName.value,
                lastName: newUserForm.lastName.value,
                age: newUserForm.age.value,
                email: newUserForm.email.value,
                password: newUserForm.password.value,
                roles: newUserRoles
            })
        })
        if (response.ok) {
            await getTableWithUsers();
            $('#user-table-tab').click();
            setTimeout(newUserForm.reset(), 1000);
        } else {
            response.json().then(errors => {
                alert(errors);
            });
        }
    });
}


//Создание модального окна
async function theModal(form, modal, id) {
    modal.show();
    let user = await fetch('http://localhost:8080/api/admin/' + id)
        .then(response => response.json());
    form.id.value = user.id;
    form.firstName.value = user.firstName;
    form.lastName.value = user.lastName;
    form.age.value = user.age;
    form.email.value = user.email;
    for (let j= 0; j < user.roles.length; j++) {
        for (let i = 0; i < form.roles.options.length; i++) {
            if (form.roles.options[i].text === user.roles[j].name.split('_')[1]) {
                form.roles.options[i].selected = true;
            }
        }
    }
}

//Очищение модальных окон после закрытия
function clearModal() {
    $('#deleteUserModal').on('hide.bs.modal', function () {
        document.forms['deleteUserForm'].reset()
    })
    $('#editUserModal').on('hide.bs.modal', function () {
        document.forms['editUserForm'].reset()
    })
}

//Заполнение модального окна редактирования пользователя
async function editModalData(id) {
    let editUserForm = document.forms["editUserForm"];
    const modal = new bootstrap.Modal(document.querySelector('#editUserModal'));
    await theModal(editUserForm, modal, id);
}

//Заполнение модального окна удаления пользователя
async function deleteModalData(id) {
    let userDeleteForm = document.forms["deleteUserForm"];
    const modal = new bootstrap.Modal(document.querySelector('#deleteUserModal'));
    await theModal(userDeleteForm, modal, id);
}

//Редактирование пользователя
function editUser() {
    let editUserForm = document.forms["editUserForm"];
    editUserForm.addEventListener("submit", async ev => {
        ev.preventDefault();
        let editUserRoles = setRoles(editUserForm)
        let response = await fetch("http://localhost:8080/api/admin/", {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': csrfToken
            },
            body: JSON.stringify({
                id: editUserForm.id.value,
                firstName: editUserForm.firstName.value,
                lastName: editUserForm.lastName.value,
                age: editUserForm.age.value,
                email: editUserForm.email.value,
                password: editUserForm.password.value,
                roles: editUserRoles
            })
        });
        if (response.ok) {
            $('#editFormCloseButton').click();
            await getTableWithUsers();
        } else {
            response.json().then(errors => {
                alert(errors);
            });
        }
    });
}

//Удаление пользователя
function deleteUser() {
    let userDeleteForm = document.forms["deleteUserForm"];
    userDeleteForm.addEventListener("submit", async ev => {
        ev.preventDefault();
        let response = await fetch("http://localhost:8080/api/admin/" + userDeleteForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': csrfToken
            }
        })

        if (response.ok) {
            $('#deleteFormCloseButton').click();
            await getTableWithUsers();
        }
    });
}
