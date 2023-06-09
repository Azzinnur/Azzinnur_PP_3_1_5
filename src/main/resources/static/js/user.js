$(document).ready(async () => {
    let user = await fetch('http://localhost:8080/api/user')
        .then(result => result.json())
    document.querySelector(`#header-mail`).textContent = `${user.email}`
    let roleNames = ''
    user.roles.forEach(role => {
        let roleName = role.name.split('_')[1]
        roleNames += `${roleName} `
    })
    document.querySelector(`#header-roles`).textContent = `${roleNames}`
    let table = document.querySelector('.table-striped tbody')
    let tableFilling = `
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${roleNames}</td>
                        </tr>
                `
    table.innerHTML = tableFilling
})