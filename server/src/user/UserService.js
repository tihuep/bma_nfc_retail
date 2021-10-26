const {query} = require("../DatabaseConnector");

function findAll() {
    return query('SELECT * FROM user');
}

function findById(id) {
    return query('SELECT * FROM user WHERE id = ?', id).then(result => result[0]);
}

function insert(user) {
    query('INSERT INTO user (id, first_name, last_name, email, password) VALUES (?, ?, ?, ?, ?)',
        user.id, user.first_name, user.last_name, user.email, user.password);
    return findById(user.id);
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

module.exports = {
    findAll,
    findById,
    insert,
    update,
    deleteById
}