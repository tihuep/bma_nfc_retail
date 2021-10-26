const {query} = require("../DatabaseConnector");

function findAll() {
    return query('SELECT * FROM payment_method');
}

function findById(id) {
    return query('SELECT * FROM payment_method WHERE id = ?', [id]).then(result => result[0]);
}

module.exports = {
    findAll,
    findById
}
