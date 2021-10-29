const {query} = require("../DatabaseConnector");
const {v4: uuidV4} = require("uuid")
const bcrypt = require("bcrypt");
const {reject} = require("bcrypt/promises");

function findAll() {
    return query('SELECT * FROM user');
}

function findById(id) {
    return query('SELECT * FROM user WHERE id = ?', id).then(result => result[0]);
}

function findByEmail(email) {
    return query('SELECT * FROM user WHERE email = ?', email).then(result => result[0]);
}

async function insert(user) {
    const id = user.id ? user.id : uuidV4();
    const hashedPassword = await bcrypt.hash(user.password, 10);
    return query('INSERT INTO user (id, first_name, last_name, email, password) VALUES (?, ?, ?, ?, ?)',
        id, user.first_name, user.last_name, user.email, hashedPassword)
        .then(() => findById(id), err => reject(err));
}

function update(user) {
    return query('UPDATE user SET first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?',
        user.first_name, user.last_name, user.email, user.password, user.id)
        .then(() => findById(user.id), err => reject(err));
}

async function changePassword(user) {
    const hashedPassword = await bcrypt.hash(user.password, 10);
    return query('UPDATE user SET password = ? WHERE id = ?', hashedPassword, user.id)
        .then(() => findById(user.id), err => reject(err));
}

function deleteById(id) {
    const user = findById(id);
    return query('DELETE FROM user WHERE id = ?', id)
        .then(() => user, err => reject(err));
}

module.exports = {
    findAll,
    findById,
    findByEmail,
    insert,
    update,
    changePassword,
    deleteById
}
