
import React, { useState, useEffect, ChangeEvent } from "react";
import { ChevronUp, ChevronDown, ChevronsUpDown } from 'lucide-react';
import axios from 'axios';

import styles from './VolunteerHistory.module.css';

//export interface VolunteerHistoryProps {
  //prop?: string;
//}

interface VolunteerHistoryItem {
  eventId: number;
  eventName: string;
  eventDescription: string;
  location: string;
  requiredSkills: string[];
  urgency: string;
  eventDate: string;
  status: 'Confirmed' | 'Attended' | 'No_show';
}
export const VolunteerHistory = () => {
  const [volunteerHistory, setVolunteerHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [sortConfig, setSortConfig] = useState<{ key: string; direction: 'asc' | 'desc' } | null>(null);
  const [sortOrder, setSortOrder] = useState<'asc' | 'desc'>('desc');
  const [sortColumn, setSortColumn] = useState<string | null>(null);
  const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('asc');


  useEffect(() => {
    const fetchHistory = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/volunteer-history');
        setVolunteerHistory(response.data);
        console.log(response.data)
      } catch (err: any) {
        setError(err.message || 'Error loading volunteer history');
      }
    };
  
    fetchHistory();
  }, []);

  /*
  useEffect(()=>{
        //putting filteredOrders in this might cause a logic error
        setTotalPages(Math.ceil(filteredOrders.length / selectedItemsPerPage));
        setPaginatedOrders(filteredOrders.slice(
            (currentPage-1) * selectedItemsPerPage,
            currentPage * selectedItemsPerPage
        ));
    },[selectedItemsPerPage, currentPage])

  */

  const urgencyOrder = {
    High: 3,
    Medium: 2,
    Low: 1
  };

  const columns = [
    { key: 'volunteerName', label: 'Volunteer Name' },
    { key: 'eventName', label: 'Event Name' },
    { key: 'eventDescription', label: 'Event Description' },
    { key: 'eventLocation', label: 'Event Location' },
    { key: 'requiredSkills', label: 'Required Skills' },
    { key: 'urgency', label: 'Urgency' },
    { key: 'eventDate', label: 'Event Date' },
    { key: 'status', label: 'Status' },
  ];
  

  const handleSort = (column: string) => {
    if (sortColumn === column) {
      if (sortDirection === 'asc') {
        setSortDirection('desc');
      } else if (sortDirection === 'desc') {
        setSortColumn(null); // remove sorting
        setSortDirection('asc');
      }
    } else {
      setSortColumn(column);
      setSortDirection('asc');
    }
  };


/*
  const handleItemsPerPageChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setItemsPerPage(Number(event.target.value)); // Update the items per page dynamically
    setCurrentPage(1); // Reset to first page when items per page changes
  };
  */

  

const getSortedData = () => {
  if (!sortColumn) return volunteerHistory;

  return [...volunteerHistory].sort((a, b) => {
    const aValue = a[sortColumn];
    const bValue = b[sortColumn];

    // Sort urgency levels manually
    if (sortColumn === 'urgency') {
      const urgencyRank = { 'High': 3, 'Medium': 2, 'Low': 1 };
      return sortDirection === 'asc'
        ? urgencyRank[aValue] - urgencyRank[bValue]
        : urgencyRank[bValue] - urgencyRank[aValue];
    }

    // Dates
    if (sortColumn === 'eventDate') {
      return sortDirection === 'asc'
        ? new Date(aValue).getTime() - new Date(bValue).getTime()
        : new Date(bValue).getTime() - new Date(aValue).getTime();
    }

    // Default: string comparison
    return sortDirection === 'asc'
      ? String(aValue).localeCompare(String(bValue))
      : String(bValue).localeCompare(String(aValue));
  });
};


const ChevronIcon = ({ column }: { column: string }) => {
  if (sortColumn !== column) {
    return <ChevronsUpDown className="chevron hidden group-hover:inline" size={16} />;
  }
  return sortDirection === 'asc' ? (
    <ChevronUp className={styles.chevronIcon} size={16} />
  ) : (
    <ChevronDown className={styles.chevronIcon} size={16} />
  );
};



  return( 
    <div className={styles.VolunteerHistoryContainer}>
      <div className={styles.title}> Volunteer History </div>
      <div className={styles.VolunteerHisDisContainer}> 
        <table>
        <thead className={styles.VolunteerHistoryInfo}>
  <tr className={styles.columnHeader}>
    {columns.map((column) => (
      <th key={column.key} onClick={() => handleSort(column.key)} className={styles.columnHeader} style={{ cursor: 'pointer' }}>
        {column.label}
        {sortColumn === column.key && (
          sortDirection === 'asc' ? <ChevronUp size={16} /> : <ChevronDown size={16} />
        )}
      </th>
    ))}
  </tr>
</thead>
          <tbody className ={styles.VolunteerHistoryTableBody}>
          {getSortedData().map((history) => (
          <tr key={`${history['eventId']}-${history['volunteerName']}`}>
          <td>{history['volunteerName']}</td>
          <td>{history['eventName']}</td>
          <td>{history['eventDescription']}</td>
          <td>{history['eventLocation']}</td>
          <td>{history['requiredSkills']}</td>
          <td>{history['urgency']}</td>
          <td>{new Date(history['eventDate']).toLocaleString()}</td>
          <td className={styles.statusTag}>{history['status']}</td>
        </tr>
      ))}
          </tbody>
          <tfoot>

          </tfoot>
        </table>
      </div>
    </div>);
}

export default VolunteerHistory; 




