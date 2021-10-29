const express = require('express');
const router = express.Router();
const service = require('./UserService');
const authService = require('../authentication/AuthenticationService')

router.get('/', authService.authenticateToken, (request, response) => {
    service.findAll().then(result => response.json(result), error => response.status(500).send(error));
})

router.get('/:id', authService.authenticateToken, (request, response) => {
    service.findById(request.params.id).then(result => response.json(result), error => response.status(500).send(error));
})

router.put('/:id', authService.authenticateToken, (request, response) => {
    service.update(request.body).then(result => response.json(result), error => response.status(500).send(error));
})

router.put('/:id/password', authService.authenticateToken, (request, response) => {
    service.changePassword(request.body).then(result => response.json(result), error => response.status(500).send(error))
})

router.delete('/:id', authService.authenticateToken, (request, response) => {
    service.deleteById(request.params.id).then(result => response.json(result), error => response.status(500).send(error));
})

module.exports = router;
