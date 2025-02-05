import { Sidebar } from './components/Sidebar';
import { Header } from './components/Header';
import './App.css';

function App() {
  return (
    <div className="App">
      <Sidebar />
      <div className="main-content">
        <Header />
        <div className="content">
        </div>
      </div>
    </div>
  );
}

export default App;
