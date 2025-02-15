import { ReactNode } from "react";
import { BrowserRouter as Router, Routes, Route, useLocation } from "react-router-dom";
import { Homepage } from './components/Homepage';
import { Login } from './components/Login';
import { SignUp } from './components/SignUp';
import { Sidebar } from './components/Sidebar';
import { Header } from './components/Header';
import { AdminDashboard } from './components/AdminDashboard';
import { VolunteerDashboard } from './components/VolunteerDashboard';
import { VolunteerDetails } from './components/VolunteerDetails';
import { VolunteerHistory } from './components/VolunteerHistory';
import { VolunteerMatchingForm } from './components/VolunteerMatchingForm';

import './App.css';

function Layout({ children }: { children: ReactNode }) {
  const location = useLocation();
  const showHeaderAndSidebar = !['/', '/login'].includes(location.pathname);
  return (
    <div>
      {showHeaderAndSidebar && <Sidebar />}
      <div className="main-content">
        {showHeaderAndSidebar && <Header />}
        <div className="content">
          {children}
        </div>
      </div>
    </div>
  );
}

function Home() {
  return <div>Home Page</div>;
}

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Homepage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/SignUp" element={<SignUp />} />
        <Route path="/AdminDashboard" element={<Layout><AdminDashboard /></Layout>} />
        <Route path="/VolunteerDashboard" element={<Layout><VolunteerDashboard /></Layout>} />
        <Route path="/VolunteerDetails" element={<Layout><VolunteerDetails /></Layout>} />
        <Route path="/VolunteerHistory" element={<Layout><VolunteerHistory /></Layout>} />
        <Route path="/VolunteerMatchingForm" element={<Layout><VolunteerMatchingForm /></Layout>} />
      </Routes>
    </Router>
  );
}

export default App;