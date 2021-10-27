const {query} = require("../DatabaseConnector");
const {v4: uuidV4} = require("uuid")

function findAll() {
    return query('SELECT * FROM user');
}

function findById(id) {
    return query('SELECT * FROM user WHERE id = ?', id).then(result => result[0]);
}

function insert(user) {
    const id = user.id ? user.id : uuidV4();
    query('INSERT INTO user (id, first_name, last_name, email, password) VALUES (?, ?, ?, ?, ?)',
        id, user.first_name, user.last_name, user.email, user.password);
    return findById(id);
}

function update(user) {
    query('UPDATE user SET first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?',
        user.first_name, user.last_name, user.email, user.password, user.id);
    return findById(user.id);
}

function deleteById(id) {
    const user = findById(id);
    query('DELETE FROM user WHERE id = ?', id);
    return user;
}

function login(user) {
    return query('SELECT password FROM user WHERE email = ?', user.email).then(result => {
        return result[0].password === user.password;
    });
}

module.exports = {
    findAll,
    findById,
    insert,
    update,
    deleteById,
    login
}
