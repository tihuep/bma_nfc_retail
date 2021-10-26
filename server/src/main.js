const express = require('express');
const app = express();
const port = 8080;

const articleApi = require('./article/ArticleApi');
const paymentMethodApi = require('./payment_method/PaymentMethodApi');
const userApi = require('./user/UserApi');
const purchaseApi = require('./purchase/PurhcaseApi')
const articlePurchaseApi = require('./article_purchase/ArticlePurchaseApi')

app.use(express.json());

app.use('/articles', articleApi);
app.use('/payment_methods', paymentMethodApi);
app.use('/users', userApi);
app.use('/purchases', purchaseApi);
app.use('/purchases', articlePurchaseApi);

app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`);
})
