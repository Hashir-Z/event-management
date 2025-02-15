import { Sidebar } from './components/Sidebar';
import { Header } from './components/Header';
import { EventForm } from './components/EventForm';
import { AdminDashboard } from './components/AdminDashboard';
import './App.css';
import { BrowserRouter as Router, Routes, Route} from "react-router-dom";
import { VolunteerDetails } from './components/VolunteerDetails';

function App() {
  return (
    <div className="App">
      <Sidebar />
      <div className="main-content">
        <Header />
        <div className="content">
        <VolunteerDetails/>
          <AdminDashboard />
        </div>
      </div>
    </div>
  );
}

export default App;
