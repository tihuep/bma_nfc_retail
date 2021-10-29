const express = require('express');
const router = express.Router();
const service = require('./ArticleService');
const authService = require('../authentication/AuthenticationService');

router.get('/', authService.authenticateToken, (request, response) => {
    service.findAll().then(result => response.json(result), () => response.sendStatus(500));
});

router.get('/:id', authService.authenticateToken, (request, response) => {
    service.findById(request.params.id).then(result => response.json(result), () => response.sendStatus(500));
});

module.exports = router;
