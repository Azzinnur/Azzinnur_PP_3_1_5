$(document).ready(function () {
    $('.table .editButton').on('click', function (event) {
        const userEdit = document.getElementById('editUserModal')
        $('#editUserModal').on('show.bs.modal', function (event) {
            const button = event.relatedTarget
            const editId = button.getAttribute('data-id')
            userEdit.querySelector('.modal-body form').action = '/admin/saveUser/' + editId
            userEdit.querySelector('.modal-body input[id="editId"]').value = button.getAttribute('data-id')
            userEdit.querySelector('.modal-body input[id="editFirstName"]').value = button.getAttribute('data-firstName')
            userEdit.querySelector('.modal-body input[id="editLastName"]').value = button.getAttribute('data-lastName')
            userEdit.querySelector('.modal-body input[id="editAge"]').value = button.getAttribute('data-age')
            userEdit.querySelector('.modal-body input[id="editEmail"]').value = button.getAttribute('data-email')

            const optionsRole = userEdit.querySelector('.modal-body select').getElementsByTagName('option');
            console.log(optionsRole)
            let arrRoleId = button.getAttribute('data-roles').matchAll(/id=\d+/g)
            console.log(arrRoleId)
            for (const strId of arrRoleId) {
                let optId = strId.toString().slice(3)
                console.log(optId)
                for (let i = 0; i < optionsRole.length; i++) {
                    if (optionsRole[i].value === optId) {
                        optionsRole[i].selected = true
                    }
                }
            }

        })
        $('#editUserModal').on('hidden.bs.modal', function (event) {
            const optionsRole = userEdit.querySelector('.modal-body select').getElementsByTagName('option');
            for (let i = 0; i < optionsRole.length; i++) {
                optionsRole[i].selected = false
            }
        })
    })
})

$(document).ready(function () {
    $('.table .deleteButton').on('click', function (event) {
        const userDelete = document.getElementById('deleteUserModal')
        $('#deleteUserModal').on('show.bs.modal', function (event) {
            const button = event.relatedTarget
            const deleteId = button.getAttribute('data-id')
            userDelete.querySelector('.modal-body form').action = '/admin/deleteUser/' + deleteId
            userDelete.querySelector('.modal-body input[id="deleteId"]').value = button.getAttribute('data-id')
            userDelete.querySelector('.modal-body input[id="deleteFirstName"]').value = button.getAttribute('data-firstName')
            userDelete.querySelector('.modal-body input[id="deleteLastName"]').value = button.getAttribute('data-lastName')
            userDelete.querySelector('.modal-body input[id="deleteAge"]').value = button.getAttribute('data-age')
            userDelete.querySelector('.modal-body input[id="deleteEmail"]').value = button.getAttribute('data-email')

            const optionsRole = userDelete.querySelector('.modal-body select').getElementsByTagName('option');
            let arrRoleId = button.getAttribute('data-roles').matchAll(/id=\d+/g)
            for (const strId of arrRoleId) {
                let optId = strId.toString().slice(3)
                for (let i = 0; i < optionsRole.length; i++) {
                    if (optionsRole[i].value === optId) {
                        optionsRole[i].selected = true
                    }
                }
            }
        })

        $('#deleteUserModal').on('hidden.bs.modal', function (event) {
            const optionsRole = userDelete.querySelector('.modal-body select').getElementsByTagName('option');
            for (let i = 0; i < optionsRole.length; i++) {
                optionsRole[i].selected = false
            }
        })
    })
})

