const express = require('express');
const app = express();
const port = 8080;

const articleApi = require('./article/ArticleApi');
app.use(express.json());
app.use('/articles', articleApi);

app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`);
})
