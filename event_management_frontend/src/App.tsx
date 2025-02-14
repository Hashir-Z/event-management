import { Sidebar } from './components/Sidebar';
import { Header } from './components/Header';
import { AdminDashboard } from './components/AdminDashboard';
import './App.css';

function App() {
  return (
    <div className="App">
      <Sidebar />
      <div className="main-content">
        <Header />
        <div className="content">
          <AdminDashboard />
        </div>
      </div>
    </div>
  );
}

export default App;
