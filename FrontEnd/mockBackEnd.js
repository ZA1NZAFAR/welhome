const jsonServer = require('json-server')
const server = jsonServer.create()
const middlewares = jsonServer.defaults({ logger: true, bodyParser: true })

const serverRouter = jsonServer.router('./mockBackEnd.json');

server.use(middlewares);

server.get('/api/reservations', (req, res, next) => {
  const { owner_email } = req.query;
  if (owner_email) {
    const filtered = serverRouter.db.get('properties').filter({ ownerEmail: owner_email }).value();
    const propertyIds = filtered.map(property => property.id);
    const reservations = serverRouter.db.get('reservations').filter(reservation => propertyIds.includes(reservation.propertyId)).value();
    console.log(reservations);
    res.status(200).send(reservations);
  } else {
    next();
  }
});

server.get('/api/reviews/propertyId/:propertyId', (req, res, next) => {
  const { propertyId } = req.params;
  const reviews = serverRouter.db.get('reviews').filter({ propertyId }).value();
  console.log(reviews);
  res.status(200).send(reviews);
});

server.get('/api/queries/owner_booked_properties', (req, res, next) => {
  const { owner_email } = req.query;
  if (owner_email) {
    const filtered = serverRouter.db.get('properties').filter({ ownerEmail: owner_email }).value();
    const propertyIds = filtered.map(property => property.id);
    const reservations = serverRouter.db.get('reservations').filter(reservation => propertyIds.includes(reservation.propertyId)).value();
    const result = {
      owner: {
        email: owner_email
      },
      properties: filtered,
      bookings: reservations
    }
    console.log(result);
    res.status(200).send(result);
  } else {
    res.status(400).send({ error: 'owner_email is required' });
  }
});

server.use(jsonServer.rewriter({
  "/api/properties/property*": "/api/properties$1",
  "/api/reservations/renter_email/:email": "/api/reservations?renterEmail=:email",
  "/api/profiles/:email": "/api/profiles?email=:email",
  "/api/reviews/property_id*": "/api/reviews/propertyId*"
}))

server.use('/api', serverRouter);

server.listen(9092, () => {
  console.log('JSON Server is running')
})