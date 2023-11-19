import logo from './logo.svg';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import './App.css';
import CategoriaProds from './pages/CategoriaProds';
import Virtualbox from './pages/Home';

function App() {
  return (
 <Router>
  <div className=''>
     <Routes>
     <Route path="/" element={<Virtualbox/>} />
     <Route path="/categoria" element={<CategoriaProds />} />
     </Routes>
  </div>
   </Router>
  );
}

export default App;
