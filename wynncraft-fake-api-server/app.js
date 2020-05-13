const express = require('express');
const cors = require('cors');
const app = express();

/*
const player = {
    uuid: "4380438ac91f4355a61f1ffdfb0c3bd5",
    name: "Ikeetjeop",
    health: 100, maxHealth: 200,
    x: 900, y: 64, z: -1043,
    party: [
        {
            uuid: "069a79f444e94726a5befca90e38aaf5",
            name: "Notch",
            health: 1, maxHealth: 200,
            x: 10, y: 64, z: -1043
        },
        {
            uuid: "63280ad07a0345c7962a22e6bb6530c3",
            name: "DaqEm",
            health: 1, maxHealth: 200,
            x: 400, y: 64, z: -1043
        }

    ]
}
 */


let player = {};

app.use(express.json());

// GET method route
app.get('/map/getMyLocation', cors(), function (req, res) {
    res.send(player);
})

app.post('/map/getMyLocation', cors(), function (req, res) {
    player = req.body;
    res.sendStatus(200);
})

const port = process.env.PORT || 44889;

app.listen(port, args => {
    console.log("Fake API server listening on http://localhost:%s", port);
})