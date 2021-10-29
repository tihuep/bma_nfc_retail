const express = require('express');
const router = express.Router();
const service = require('./PurchaseService');
const authService = require('../authentication/AuthenticationService');

router.get('/', authService.authenticateToken, (request, response) => {
    service.findAllByUserId(request.user.id).then(result => response.json(result), () => response.sendStatus(500));
})

router.get('/user/:user_id', authService.authenticateToken, (request, response) => {
    service.findAllByUserId(request.params.user_id).then(result => response.json(result), () => response.sendStatus(500));
})

router.get('/:id', authService.authenticateToken, (request, response) => {
    service.findById(request.params.id).then(result => response.json(result), () => response.sendStatus(500));
})

router.post('/', authService.authenticateToken, (request, response) => {
    service.insert(request.body).then(result => response.json(result), () => response.sendStatus(500));
})

router.put('/:id', authService.authenticateToken, (request, response) => {
    service.update(request.body).then(result => response.json(result), () => response.sendStatus(500));
})

router.delete('/:id', authService.authenticateToken, (request, response) => {
    service.deleteById(request.params.id).then(result => response.json(result), () => response.sendStatus(500));
})

module.exports = router;
