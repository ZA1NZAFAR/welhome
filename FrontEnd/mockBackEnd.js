const jsonServer = require('json-server')
const server = jsonServer.create()
const middlewares = jsonServer.defaults({ logger: true, bodyParser: true })

const serverRouter = jsonServer.router('./mockBackEnd.json');

server.use(middlewares);

server.get('/api/reservations', (req, res, next) => {
  const { owner_email } = req.query;
  if (owner_email) {
    const filtered = serverRouter.db.get('properties').filter({ owner_email }).value();
    const propertyIds = filtered.map(property => property.id);
    const reservations = serverRouter.db.get('reservations').filter(reservation => propertyIds.includes(reservation.property_id)).value();
    console.log(reservations);
    res.status(200).send(reservations);
  } else {
    next();
  }
});

server.use(jsonServer.rewriter({
  "/api/properties/property*": "/api/properties$1",
  "/api/reservations/renter_email/:email": "/api/reservations/?renter_email=:email"
}))

server.use('/api', serverRouter);

server.listen(9092, () => {
  console.log('JSON Server is running')
})