const mysql = require('mysql')
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'bma',
    password: 'bma'
})

connection.connect();
connection.query('USE nfc_retail;')

function query(queryString, queryParams) {
    return new Promise((resolve, reject) => {
        const sql = mysql.format(queryString, queryParams);
        connection.query(sql, (err, result) => {
            if (err) return reject(err);
            resolve(result);
        })
    })
}

module.exports = {
    query
}
