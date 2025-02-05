import styles from './Header.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser, faBell } from '@fortawesome/free-solid-svg-icons';

export function Header() {
  return (
    <div className={styles.Header}>
      <div className={styles.logoContainer}>
        <img src="logo-white.svg" alt="logo" className={styles.logoImg} />
      </div>
      <div className={styles.headerContainer}>
        <div className={styles.logoText}>
          EventAid
        </div>
        <div className={styles.iconsContainer}>
          <FontAwesomeIcon icon={faBell} className={styles.icon} />
          <FontAwesomeIcon icon={faUser} className={styles.icon} />
        </div>
      </div >
    </div >
  );
}
