require('dotenv').config();

const express = require('express');
const router = express.Router();
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
const userService = require('../user/UserService');

router.post('/register', async (request, response) => {
    const user = request.body;
    if (user === 'undefined') response.sendStatus(400);
    userService.insert(user).then(result => {
        const token = jwt.sign(user, process.env.ACCESS_TOKEN_SECRET)
        response.json({
            token,
            user: result
        })
    });
})

router.post('/login', async (request, response) => {
    const user = await userService.findByEmail(request.body.email);
    if (user === 'undefined') response.sendStatus(400);

    try {
        if (!(await bcrypt.compare(request.body.password, user.password))) {
            response.sendStatus(400);
        } else {
            const token = jwt.sign(JSON.stringify(user), process.env.ACCESS_TOKEN_SECRET)
            response.json({
                token,
                user
            })
        }
    } catch (err) {
        console.error(err);
        response.sendStatus(500);
    }

})

module.exports = router;
