const GoogleStrategy = require("passport-google-oauth20").Strategy;
const User = require("../models/User");
const axios = require("axios");

module.exports = function (passport) {
  passport.use(
    new GoogleStrategy(
      {
        clientID: process.env.GOOGLE_CLIENT_ID,
        clientSecret: process.env.GOOGLE_CLIENT_SECRET,
        callbackURL: "/auth/google/callback",
      },
      async (accessToken, refreshToken, profile, done) => {
        try {
          let user = await User.findOne({ googleId: profile.id });

          if (user) {
            done(null, user);
          } else {
            // Send a request to the Hibernate backend to create a new user
            const newUser = {
              googleId: profile.id,
              displayName: profile.displayName,
              email: profile.emails[0].value,
            };
            const HIBERNATE_BACKEND_URL = process.env.HIBERNATE_BACKEND_URL || "http://localhost:8080";

            const response = await axios.post(
              `${HIBERNATE_BACKEND_URL}/api/users`,
              newUser
            );

            user = response.data;

            done(null, user);
          }
        } catch (error) {
          console.error(error);
          done(error, null);
        }
      }
    )
  );

  passport.serializeUser((user, done) => {
    done(null, user.id);
  });

  passport.deserializeUser(async (id, done) => {
    try {
      const user = await User.findById(id);
      done(null, user);
    } catch (error) {
      done(error, null);
    }
  });
};
