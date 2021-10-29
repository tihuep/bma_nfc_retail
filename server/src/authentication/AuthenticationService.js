require('dotenv').config();
const jwt = require('jsonwebtoken');

function authenticateToken(request, response, next) {
    const authorizationHeader = request.headers.authorization;
    const token = authorizationHeader && authorizationHeader.split(' ')[1];
    if (token == null) return response.sendStatus(401);

    jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (error, user) => {
        if (error) return response.sendStatus(403);
        request.user = user;
        next();
    })
}

module.exports = { authenticateToken };
