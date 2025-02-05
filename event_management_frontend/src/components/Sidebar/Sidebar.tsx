import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSignOutAlt, faCog, faHome } from '@fortawesome/free-solid-svg-icons';
import styles from './Sidebar.module.css';

export function Sidebar() {
  return (
    <div className={styles.sidebar}>
      <div className={styles.sidebarContainer}>
        <hr className={styles.divider} />
        <div className={styles.menu}>
          <FontAwesomeIcon icon={faHome} className={styles.icon} />
        </div>
        <div className={styles.bottomIcons}>
          <FontAwesomeIcon icon={faCog} className={styles.icon} />
          <FontAwesomeIcon icon={faSignOutAlt} className={styles.icon} />
        </div>
      </div>
    </div>
  );
}
