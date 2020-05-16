$(document).ready(function () {
    getJson3();
    clearTeble();
    addUser();
    validatorAdd('#formAdd');
    validatorAdd('#editUserForm');
});

// Ajax get all users
function getJson3() {
    $.ajax({
        url: 'http://localhost:8080/api/',
        cache: false,
        type: 'GET',
        contentType: "application/json;charset=UTF-8",
        dataType: 'json',
        success: function (data) {
            data.forEach(function (element) {
                addRow(element);
            })
        },
        error: function () {
            alert("Ошибка выполнения");
        }
    });
}

// Формирование таблицы
function addRow(user) {
    let id = user.id;
    let name = user.userName;
    let lastName = user.lastName;
    let email = user.email;
    let age = user.age;
    let roles = user.roles;
    let addTd = `<tr id = "${id}">
                    <td id = "userId_${id}">${id}</td>
                    <td id = "userName_${id}">${name}</td>
                    <td id = "lastName_${id}">${lastName}</td>
                    <td id = "age_${id}">${age}</td>
                    <td id = "email_${id}">${email}</td>
                    <td id = "userRoles_${id}">${roles}</td>
                    <td><p><button id="editUser_${id}" name="editUserBtn" class="btn btn-info edit-user" data-toggle="modal">edit</button></p></td>
                    <td><p><button id="deleteUser_${id}" name="deleteUserBtn" class="btn btn-info delete-user" data-toggle="modal">delete</button></p></td>
                </tr>'`;
    $('#usersTableTbody').append(addTd);
}

// Add user
function addUser() {
    $("#btnAddSubmit").click(
        function () {
            // validatorAdd('#formAdd');
            if (!($('#formAdd').valid())) return;
            let userName = document.getElementById("userName").value;
            let lastName = document.getElementById("lastName").value;
            let password = document.getElementById("password").value;
            let age = document.getElementById("age").value;
            let email = document.getElementById("email").value;
            let roles = [];
            let rolesFromHtml = document.getElementById("rolesFromHtml").value;
            if (rolesFromHtml === 'adminAndUser') {
                roles.push("ADMIN");
                roles.push("USER");
            }
            if (rolesFromHtml === 'admin') {
                roles.push("ADMIN");
            }
            if (rolesFromHtml === 'user') {
                roles.push("USER");
            }
            let data = {
                password: password,
                email: email,
                age: age,
                roles: roles,
                userName: userName,
                lastName: lastName
            };
            let serializedUser = JSON.stringify(data);
            $.ajax({
                url: 'http://localhost:8080/api/',
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                data: serializedUser,
                success: function () {
                    clearTeble();
                    getJson3();
                    $("#navItemNewUser").removeClass("active");
                    $("#newUser").removeClass("active show");
                    $('#navItemMain').addClass("active");
                    $('#allUsers').addClass("active show");
                },
                error: function () {
                    alert("add not success :( ");
                }
            });
        }
    )
}

// Edit User Modal
$('#usersTableTbody').on('click', '.edit-user', function () {
    $('#editUserModal').modal();
    let id = this.id.slice(this.id.lastIndexOf('_') + 1);
    setValueInModal(id);
});

//Edit User Ajax
$('#editUserBtn').click(
    function () {
        let id = document.getElementById("idEdit").value;
        let userName = document.getElementById("firstNameEdit").value;
        let lastName = document.getElementById('lastNameEdit').value;
        let password = document.getElementById('passwordEdit').value;
        let age = document.getElementById('ageEdit').value;
        let email = document.getElementById('emailEdit').value;
        let roles = [];
        let rolesFromHtml = document.getElementById('rolesEdit').value;
        if (rolesFromHtml === 'adminAndUser') {
            roles.push("ADMIN");
            roles.push("USER");
        }
        if (rolesFromHtml === 'admin') {
            roles.push("ADMIN");
        }
        if (rolesFromHtml === 'user') {
            roles.push("USER");
        }
        let data = {
            id: id,
            password: password,
            email: email,
            age: age,
            roles: roles,
            userName: userName,
            lastName: lastName
        };
        let serializedUser = JSON.stringify(data);
        $.ajax({
            url: 'http://localhost:8080/api/',
            type: "PUT",
            contentType: "application/json;charset=UTF-8",
            data: serializedUser,
            success: function () {
                clearTeble();
                getJson3();
                $('#editUserModal').modal('hide');
                $("#navItemNewUser").removeClass("active");
                $('#navItemMain').addClass("active");
            },
            error: function () {
                alert("add not success :( ");
            }
        });
    });

//Delete Modal
$('#usersTableTbody').on('click', '.delete-user', function () {
    $('#delteUserModal').modal();
    let id = this.id.slice(this.id.lastIndexOf('_') + 1);
    setValueInModal(id);
});

// Delete Ajax
$('#deleteUserBtn').click(
    function () {
        let id = document.getElementById("idEdit").value;
        $.ajax({
            url: 'http://localhost:8080/api/' + id,
            type: "DELETE",
            contentType: "application/json;charset=UTF-8",
            success: function () {
                clearTeble();
                getJson3();
                $('#delteUserModal').modal('hide');
                $("#navItemNewUser").removeClass("active");
                $('#navItemMain').addClass("active");
            },
            error: function () {
                alert("add not success :( ");
            }
        });
    }
);

// Set value in modal
function setValueInModal(id) {
    let roles = $("#userRoles_" + id).text();

    $('.idEdit').attr('value', id);
    $('.firstNameEdit').attr('value', $("#userName_" + id).text());
    $('.lastNameEdit').attr('value', $("#lastName_" + id).text());
    $('.passwordEdit').attr('value', '');
    $('.passwordEdit').attr('placeholder', '');
    $('.ageEdit').attr('value', $("#age_" + id).text());
    $('.emailEdit').attr('value', $("#email_" + id).text());

    if (roles === 'USER,ADMIN') $('.rolesEdit [value="adminAndUser"]').attr('selected', 'selected');
    if (roles === 'USER') $('.rolesEdit [value="user"]').attr('selected', 'selected');
    if (roles === 'ADMIN') $('.rolesEdit [value="admin"]').attr('selected', 'selected');
};

// Clear Table
function clearTeble() {
    for (; document.getElementById('allUsersTable').getElementsByTagName('tr').length > 1;) {
        document.getElementById('allUsersTable').deleteRow(1);
    }
}

// Валидация форм
function validatorAdd(id) {
    $(id).validate({
        rules: {
            userName: {
                required: true,
                minlength: 3,
                maxlength: 15
            },
            lastName: {
                required: true,
                minlength: 3,
                maxlength: 15
            },
            email: {
                required: true,
                email: true,
                minlength: 3,
                maxlength: 25
            },
            password: {
                required: true,
                minlength: 3,
                maxlength: 15
            },
            age: {
                required: true,
                digits: true,
                range: [0, 99]
            }
        },
        messages: {
            userName: {
                required: "Поле 'First Name' обязательно к заполнению",
                minlength: "Введите не менее 3-х символов в поле 'First Name'",
                maxlength: "Введите не более 15-ти символов в поле 'First Name'"
            },
            age: {
                required: "Поле 'age' обязательно к заполнению",
                digits: "Только цифры",
                range: "Возраст должен быть в диапозоне 0 - 99"
            },
            lastName: {
                required: "Поле 'Last Name' обязательно к заполнению",
                minlength: "Введите не менее 3-х символов в поле 'Last Name'",
                maxlength: "Введите не более 15-ти символов в поле 'Last Name'"
            },
            email: {
                required: "Поле 'Email' обязательно к заполнению",
                minlength: "Введите не менее 3-х символов в поле 'Email'",
                maxlength: "Введите не более 25-ти символов в поле 'Email'",
                email: "Email должен состоять из (Имя емаила)@(email).(домен)"
            },
            password: {
                required: "Поле 'password' обязательно к заполнению",
                minlength: "Введите не менее 3-х символов в поле 'Password'",
                maxlength: "Введите не более 15-ти символов в поле 'Password'"
            }
        }
    });
};

// $("input").focus(function () {
//     validatorAdd('#formAdd');
// });


