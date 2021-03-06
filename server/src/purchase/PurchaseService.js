const {query} = require("../DatabaseConnector");
const {v4: uuidV4} = require("uuid")
const articlePurchaseService = require("../article_purchase/ArticlePurchaseService")

function findAllByUserId(user_id) {
    return query('SELECT * FROM purchase WHERE user_id = ?', user_id)
}

function findById(id) {
    return query('SELECT * FROM purchase WHERE id = ?', id).then(result => {
        const purchase = result[0];
        return articlePurchaseService.findAllByPurchaseId(purchase.id).then(articlePurchases => {
            purchase.items = articlePurchases;
            return purchase;
        });
    });
}

function insert(purchase) {
    const id = purchase.id ? purchase.id : uuidV4();
    query('INSERT INTO purchase (id, user_id) VALUES (?, ?)', id, purchase.user_id);
    return findById(id);
}

function update(purchase) {
    query('UPDATE purchase SET user_id = ? WHERE id = ?', purchase.user_id, purchase.id);
    return findById(purchase.id);
}

function deleteById(id) {
    const purchase = findById(id);
    query('DELETE FROM purchase WHERE id = ?', id);
    return purchase;
}

module.exports = {
    findAllByUserId,
    findById,
    insert,
    update,
    deleteById
};
