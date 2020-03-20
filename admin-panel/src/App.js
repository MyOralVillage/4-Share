import React from "react";
import "./App.css";
import MainPage from "./MainPage";

const countries = [
  { country: "Canada", currency: "CAD", image: "Canada.png" },
  { country: "Bangladesh", currency: "BDT", image: "Bangladesh.png" },
  { country: "India", currency: "INR", image: "India.png" },
  { country: "Pakistan", currency: "PKR", image: "Pakistan.png" },
  { country: "Kenya", currency: "KES", image: "Kenya.png" },
  { country: "United States", currency: "USD", image: "United_States.png" }
];

function App() {
  return (
    <div className="App">
      <MainPage countries={countries} />
    </div>
  );
}

export default App;
