const {query} = require("../DatabaseConnector");

function findAll() {
    return query('SELECT * FROM article');
}

function findById(id) {
    return query('SELECT * FROM article WHERE id = ?', [id]).then(result => result[0]);
}

module.exports = {
    findAll,
    findById
}
