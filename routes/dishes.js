const express = require('express');
var HttpStatus = require('http-status-codes');
const router = express.Router();


router.route('/')
    .get((req, res) => {
        res.json([
            {
                id: 128219,
                title: "Pommes"
            },
            {
                id: 821,
                title: "Bratwurst"
            }
        ]);
    })
    .post((req, res) => {
        res.status(HttpStatus.NOT_IMPLEMENTED).send();
    })
    .put((req, res) => {
        res.status(HttpStatus.IM_A_TEAPOT).send();
    })
    .patch((req, res) => {
        res.status(HttpStatus.UNAUTHORIZED).send();
    })

module.exports = router;