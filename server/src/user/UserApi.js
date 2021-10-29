const express = require('express');
const router = express.Router();
const service = require('./UserService');
const authService = require('../authentication/AuthenticationService')

router.get('/', authService.authenticateToken, (request, response) => {
    service.findAll().then(result => response.json(result));
})

router.get('/:id', authService.authenticateToken, (request, response) => {
    service.findById(request.params.id).then(result => response.json(result));
})

router.put('/:id', authService.authenticateToken, (request, response) => {
    service.update(request.body).then(result => response.json(result), () => response.sendStatus(500));
})

router.delete('/:id', authService.authenticateToken, (request, response) => {
    service.deleteById(request.params.id).then(result => response.json(result), () => response.sendStatus(500));
})

module.exports = router;
