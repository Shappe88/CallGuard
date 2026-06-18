const API_BASE = '/api/v1/admin';

// -------- Auth Page Logic (index.html) --------
if (document.getElementById('auth-form')) {
  let isLogin = true;
  const form = document.getElementById('auth-form');
  const title = document.getElementById('auth-title');
  const subtitle = document.getElementById('auth-subtitle');
  const submitBtn = document.getElementById('submit-btn');
  const toggleLink = document.getElementById('toggle-link');
  const toggleText = document.getElementById('toggle-text');
  const errorMsg = document.getElementById('error-msg');

  // Check if already logged in
  if (localStorage.getItem('adminToken')) {
    window.location.href = '/dashboard.html';
  }

  toggleLink.addEventListener('click', (e) => {
    e.preventDefault();
    isLogin = !isLogin;
    errorMsg.style.display = 'none';
    
    if (isLogin) {
      title.textContent = 'Admin Login';
      subtitle.textContent = 'Sign in to manage the CallGuard blocklist';
      submitBtn.textContent = 'Sign In';
      toggleText.textContent = "Don't have an account?";
      toggleLink.textContent = 'Register here';
    } else {
      title.textContent = 'Admin Registration';
      subtitle.textContent = 'Create a new admin account';
      submitBtn.textContent = 'Register';
      toggleText.textContent = "Already have an account?";
      toggleLink.textContent = 'Sign in here';
    }
  });

  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    errorMsg.style.display = 'none';
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const endpoint = isLogin ? '/login' : '/register';

    submitBtn.textContent = 'Processing...';
    submitBtn.disabled = true;

    try {
      const res = await fetch(`${API_BASE}${endpoint}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });
      const data = await res.json();

      if (!res.ok) throw new Error(data.error || 'Authentication failed');

      localStorage.setItem('adminToken', data.token);
      window.location.href = '/dashboard.html';
    } catch (err) {
      errorMsg.textContent = err.message;
      errorMsg.style.display = 'block';
    } finally {
      submitBtn.textContent = isLogin ? 'Sign In' : 'Register';
      submitBtn.disabled = false;
    }
  });
}


// -------- Dashboard Page Logic (dashboard.html) --------
if (document.getElementById('numbers-table-body')) {
  const token = localStorage.getItem('adminToken');
  if (!token) {
    window.location.href = '/';
  }

  const tbody = document.getElementById('numbers-table-body');
  const totalRules = document.getElementById('total-rules');
  const logoutBtn = document.getElementById('logout-btn');
  const modalOverlay = document.getElementById('modal-overlay');
  const addBtn = document.getElementById('open-add-modal');
  const closeModalBtn = document.getElementById('close-modal');
  const contactForm = document.getElementById('contact-form');
  const typeSelect = document.getElementById('type');
  const blockedFields = document.getElementById('blocked-fields');
  const allowlistedFields = document.getElementById('allowlisted-fields');
  const modalErrorMsg = document.getElementById('modal-error-msg');
  const modalTitle = document.getElementById('modal-title');

  let currentNumbers = [];

  const getHeaders = () => ({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  });

  const loadNumbers = async () => {
    try {
      const res = await fetch(`${API_BASE}/numbers`, { headers: getHeaders() });
      if (res.status === 401) {
        localStorage.removeItem('adminToken');
        window.location.href = '/';
        return;
      }
      currentNumbers = await res.json();
      renderTable();
    } catch (err) {
      console.error('Failed to load numbers', err);
    }
  };

  const renderTable = () => {
    totalRules.textContent = currentNumbers.length;
    tbody.innerHTML = '';

    currentNumbers.forEach(num => {
      const isBlocked = num.type === 'BLOCKED';
      const badgeClass = isBlocked ? 'badge-blocked' : 'badge-allowlisted';
      const badgeText = isBlocked ? 'Blocked' : 'Allowlisted';
      const detailText = isBlocked 
        ? `${num.severity || 'N/A'} • ${num.reasonCode || 'N/A'}` 
        : `${num.category || 'N/A'}`;

      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td style="font-weight: 500;">${num.normalizedNumber}</td>
        <td><span class="badge ${badgeClass}">${badgeText}</span></td>
        <td>${num.displayLabel || '-'}</td>
        <td style="color: var(--text-secondary); font-size: 0.85rem;">${detailText}</td>
        <td>
          <button class="action-btn" onclick="editNumber(${num.id})">✎</button>
          <button class="action-btn delete" onclick="deleteNumber(${num.id})">✖</button>
        </td>
      `;
      tbody.appendChild(tr);
    });
  };

  // Modal logic
  const openModal = (numberData = null) => {
    modalErrorMsg.style.display = 'none';
    contactForm.reset();
    
    if (numberData) {
      modalTitle.textContent = 'Edit Contact';
      document.getElementById('entry-id').value = numberData.id;
      document.getElementById('normalizedNumber').value = numberData.normalizedNumber;
      document.getElementById('normalizedNumber').disabled = true; // Can't edit phone number once added
      document.getElementById('displayLabel').value = numberData.displayLabel || '';
      typeSelect.value = numberData.type;
      
      if (numberData.type === 'BLOCKED') {
        document.getElementById('reasonCode').value = numberData.reasonCode || 'FRAUD';
        document.getElementById('severity').value = numberData.severity || 'HIGH';
      } else {
        document.getElementById('category').value = numberData.category || 'SOC';
      }
    } else {
      modalTitle.textContent = 'Add New Contact';
      document.getElementById('entry-id').value = '';
      document.getElementById('normalizedNumber').disabled = false;
    }
    
    toggleConditionalFields();
    modalOverlay.classList.add('active');
  };

  const closeModal = () => {
    modalOverlay.classList.remove('active');
  };

  const toggleConditionalFields = () => {
    if (typeSelect.value === 'BLOCKED') {
      blockedFields.classList.add('active');
      allowlistedFields.classList.remove('active');
    } else {
      blockedFields.classList.remove('active');
      allowlistedFields.classList.add('active');
    }
  };

  // Event Listeners
  logoutBtn.addEventListener('click', () => {
    localStorage.removeItem('adminToken');
    window.location.href = '/';
  });

  addBtn.addEventListener('click', () => openModal());
  closeModalBtn.addEventListener('click', closeModal);
  typeSelect.addEventListener('change', toggleConditionalFields);

  contactForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    modalErrorMsg.style.display = 'none';

    const id = document.getElementById('entry-id').value;
    const payload = {
      normalizedNumber: document.getElementById('normalizedNumber').value,
      displayLabel: document.getElementById('displayLabel').value,
      type: typeSelect.value
    };

    if (payload.type === 'BLOCKED') {
      payload.reasonCode = document.getElementById('reasonCode').value;
      payload.severity = document.getElementById('severity').value;
    } else {
      payload.category = document.getElementById('category').value;
    }

    try {
      let res;
      if (id) {
        // Update existing
        res = await fetch(`${API_BASE}/numbers/${id}`, {
          method: 'PUT',
          headers: getHeaders(),
          body: JSON.stringify(payload)
        });
      } else {
        // Create new
        res = await fetch(`${API_BASE}/numbers`, {
          method: 'POST',
          headers: getHeaders(),
          body: JSON.stringify(payload)
        });
      }

      const data = await res.json();
      if (!res.ok) throw new Error(data.error || 'Failed to save');

      closeModal();
      loadNumbers();
    } catch (err) {
      modalErrorMsg.textContent = err.message;
      modalErrorMsg.style.display = 'block';
    }
  });

  // Global functions for inline handlers
  window.editNumber = (id) => {
    const num = currentNumbers.find(n => n.id === id);
    if (num) openModal(num);
  };

  window.deleteNumber = async (id) => {
    if (!confirm('Are you sure you want to delete this number? Devices will remove it on next sync.')) return;
    
    try {
      const res = await fetch(`${API_BASE}/numbers/${id}`, {
        method: 'DELETE',
        headers: getHeaders()
      });
      if (res.ok) {
        loadNumbers();
      }
    } catch (err) {
      alert('Failed to delete number');
    }
  };

  // Initialization
  loadNumbers();
}
