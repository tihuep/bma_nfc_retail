const {query} = require("../DatabaseConnector");

function findAllByPurchaseId(purchaseId) {
    return query('SELECT * FROM article_purchase WHERE purchase_id = ?', purchaseId);
}

function findByArticleIdAndPurchaseId(articleId, purchaseId) {
    return query('SELECT * FROM article_purchase WHERE purchase_id = ? AND article_id = ?', purchaseId, articleId).then(result => result[0]);
}

function insert(articlePurchase) {
    query('INSERT INTO article_purchase (amount, purchase_id, article_id) VALUES (?, ?, ?)',
        articlePurchase.amount, articlePurchase.purchase_id, articlePurchase.article_id);
    return findByArticleIdAndPurchaseId(articlePurchase.article_id, articlePurchase.purchase_id);
}

function update(articlePurchase) {
    query('UPDATE article_purchase SET amount = ? WHERE article_id = ? AND purchase_id = ?',
        articlePurchase.amount, articlePurchase.article_id, articlePurchase.purchase_id);
    return findByArticleIdAndPurchaseId(articlePurchase.article_id, articlePurchase.purchase_id);
}

function deleteByPurchaseIdAndArticleId(articleId, purchaseId) {
    const purchase = findByArticleIdAndPurchaseId(articleId, purchaseId);
    query('DELETE FROM article_purchase WHERE purchase_id = ? AND article_id = ?', purchaseId,  articleId);
    return purchase;
}

module.exports = {
    findAllByPurchaseId,
    findByArticleIdAndPurchaseId,
    insert,
    update,
    deleteByPurchaseIdAndArticleId
};
