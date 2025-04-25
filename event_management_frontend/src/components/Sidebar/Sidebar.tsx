import { useLocation, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSignOutAlt, faCog, faHome } from "@fortawesome/free-solid-svg-icons";
import styles from "./Sidebar.module.css";
import AuthService from "../../services/AuthService";

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

export function Sidebar() {
  const query = useQuery();
  const type = query.get("type");
  const navigate = useNavigate();

  const goToSettings = () => {
    if (type === "admin") {
      navigate("/AdminDashboard");
    } else {
      navigate("/VolunteerDetails");
    }
  };

  const logout = async () => {
    try {
      await AuthService.logout();

      if (type === "admin") {
        navigate("/login?type=admin");
      } else {
        navigate("/login?type=volunteer");
      }
    } catch (error) {
      console.error("Error during logout:", error);
    }
  };

  const goToHomepage = () => {
    if (type === "admin") {
      navigate("/AdminDashboard");
    } else {
      navigate("/VolunteerDashboard");
    }
  };

  return (
    <div className={styles.sidebar}>
      <div className={styles.logoContainer}>
        <img src="logo-white.svg" alt="logo" className={styles.logoImg} />
        <hr className={styles.divider} />
      </div>
      <div className={styles.menu}>
        <FontAwesomeIcon
          icon={faHome}
          className={styles.icon}
          onClick={goToHomepage}
        />
      </div>
      <div className={styles.bottomIcons}>
        <FontAwesomeIcon
          icon={faCog}
          className={styles.icon}
          onClick={goToSettings}
        />
        <FontAwesomeIcon
          icon={faSignOutAlt}
          className={styles.icon}
          onClick={logout}
        />
      </div>
    </div>
  );
}
