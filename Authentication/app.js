const express = require("express");
const passport = require("passport");
const cors = require("cors");
const jwt = require("jsonwebtoken");
const dotenv = require("dotenv");

require("./config/passport-setup");

dotenv.config();

const app = express();
app.use(cors());

app.use(passport.initialize());

// Dummy route for testing
app.get("/", (req, res) => {
  res.send("Hello World!");
});

app.get(
  "/auth/google",
  passport.authenticate("google", {
    scope: ["profile", "email"],
  })
);

app.get(
  "/auth/google/callback",
  passport.authenticate("google", { failureRedirect: "/login" }),
  (req, res) => {
    // Generate a JWT token
    const token = jwt.sign({ user: req.user }, process.env.JWT_SECRET, {
      expiresIn: "1h",
    });

    // Send the JWT token to the frontend using a postMessage
    const script = `
      <script>
        window.opener.postMessage('${token}', '*');
        window.close();
      </script>
    `;
    res.send(script);
  }
);

const PORT = process.env.PORT || 3000;

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
