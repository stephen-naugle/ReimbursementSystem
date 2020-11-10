/**
 * Created by Stephen Naugle @ Revature
 */



/**
 * 

dashboard.js


 */

//Here will be the dashboard, which will be loaded from app.js

// Dashboard functionality, loading of dashboard is done in app.ja
let userId = '';

function configureDashboard() {
    mainDash();
    document.getElementById('dynamic-css').href = './css/dashboard.css';

    document.getElementById('nav-dash-btn').addEventListener('click', mainDash);
    document.getElementById('nav-ticket-table-btn').addEventListener('click', getTickets);
    document.getElementById('nav-add-btn').addEventListener('click', createTicket);

    document.getElementById('pageTitle').innerText = `Welcome, ${localStorage.getItem('username')}`;
}

function mainDash() {
    // console.log('in mainDash')
    clearBody();

    document.getElementById('dashboardBody').innerHTML =
        `<div id = "news"><span id ="newsheader"><h3>JLeague Updates</h3></span>
    <p>10-28-20<br/>Everyone needs to dress up for Halloween. No going in your normal outfits, they don't count.</p>
    <p>07-13-20<br/>Please submit your reimbursements in a timely manner. No time like the present.</p>
    <p>05-4-20<br/>Aquaman got Covid so please keep your distance and wear a mask!</p></div>
    `;
}


function createTicket() {
     console.log('in createTicket()');

    clearBody();

    let createTicketArea = document.createElement('div');
    createTicketArea.setAttribute('id', 'createTicketArea');

    createTicketArea.innerHTML = `  
    <h1 class="h3 mb-3 font-weight-normal">Enter Your Reimbusement Information</h1>
        <div id="ticket-zone">           
            <input type="number" maxlength="5" id="amount" name="amount" class="form-control" placeholder="Enter Amount" required autofocus>
            <input type="text" id="description" name="description" class="form-control" placeholder="Enter a Short Description" required autofocus>
            
                <select class="form-control" id="type">
                    <option value = "">Choose a Type</option>
                    <option value="FOOD">FOOD</option>
                    <option value="LODGING">LODGING</option>
                    <option value="TRAVEL">TRAVEL</option>
                    <option value="OTHER">OTHER</option>
                </select>
            
        <button class="btn btn-lg btn-primary btn-block" id="create-ticket-btn">Submit Ticket</button>
    </div><div id = "ticket-alert-msg">
    <p>Please fill out all fields with valid input</p>
</div>`;

    document.getElementById('dashboardBody').appendChild(createTicketArea);
    document.getElementById('ticket-alert-msg').hidden = true;
    document.getElementById('create-ticket-btn').addEventListener('click', onSubmitClick);
}

function onSubmitClick() {
    let ticketAmount = document.getElementById('amount').value;
    let ticketDescription = document.getElementById('description').value;
    let ticketType = document.getElementById('type').value;

    let ticket = [];
    ticket.push('add');
    ticket.push(localStorage.getItem('uid'));
    ticket.push(ticketAmount);
    ticket.push(ticketType);
    ticket.push(ticketDescription);
    
    if (check()) {
        document.getElementById('ticket-alert-msg').hidden = false;
    } else {
        document.getElementById('ticket-alert-msg').hidden = true;
        document.getElementById('create-ticket-btn').disabled = true;
        submitTicket(ticket);
    }
}

async function submitTicket(ticket) {
    document.getElementById('ticket-alert-msg').hidden = true;
    // console.log('in submitTicket')


//make async
    let response = await fetch('ticket', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('jwt')
        },
        body: JSON.stringify(ticket)
    });
    if (response.status == 200) {
        // console.log('ticket submitted sucessfully')
        getTickets();
    }
    else {
        // console.log(response.status);
        document.getElementById('create-ticket-btn').disabled = false;
        document.getElementById('ticket-alert-msg').hidden = false;
    }
}

async function getTickets() {
    // console.log('in getTickets()');
    clearBody();


//Make async
    let response = await fetch('ticket', {
        method: 'GET',
        mode: 'cors',
        headers: {
            'Authorization': localStorage.getItem('jwt')
        }
    });

    // console.log('Headers has role: ' + response.headers.has('UserRole'));
    // console.log('Headers has id: ' + response.headers.has('UserId'));
    let role = response.headers.get('UserRole');
    userId = response.headers.get('UserId');
    let body = await response.json();
    loadTable(body, role, userId);
}


function loadTable(response, role, id) {
    // console.log('in loadtable');

    //creates the table
    document.getElementById('dashboardBody').innerHTML =
        `<div class="table-responsive" id = "ticketTable">
    <h2>All Tickets</h2>
    <span>Filter:
    <select id="typeFilter" class="btn btn-sm btn-outline-secondary dropdown-toggle">
        <option value = "">All Types</option>
        <option value="FOOD">FOOD</option>
        <option value="LODGING">LODGING</option>
        <option value="TRAVEL">TRAVEL</option>
        <option value="OTHER">OTHER</option>
    </select>
    </span>
    <span>
    <select id="statusFilter" class="btn btn-sm btn-outline-secondary dropdown-toggle">
        <option value = "">Status - All</option>
        <option value="PENDING">PENDING</option>
        <option value="APPROVED">APPROVED</option>
        <option value="DENIED">DENIED</option>
    </select>
    </span>
     <table class="table table-striped table-sm">
       <thead>
         <tr id = "tableHead">
           <th>ID</th>
           <th>Author</th>
           <th>Amount</th>
           <th>Type</th>
           <th>Description</th>
           <th>Submission Time</th>
           <th>Resolution Time</th>
           <th>Status</th>
         </tr>
       </thead>
       <tbody id="tablebody"></tbody>
     </table>
     <div id="noTicketMsg" hidden>No tickets to display</div>
   </div>`;

    if(response.length == 0){
        document.getElementById('noTicketMsg').hidden = false;
        document.getElementById('noTicketMsg').style.backgroundColor = 'red';
    }

    for (let i = 0; i < response.length; i++) {
        let newRow = document.createElement('tr');
        newRow.setAttribute('name', `${response[i].type}`);
        newRow.setAttribute('class', 'rows');
        newRow.setAttribute('data-status', response[i].status);
        newRow.innerHTML = `
        <td>${response[i].reimbId}</td>
        <td>${response[i].authorId}</td>
        <td>${response[i].amount}</td>
        <td>${response[i].type}</td>
        <td>${response[i].ticketDescription}</td>
        <td>${response[i].timeSubmitted}</td>
        <td>${response[i].timeResolved}</td>
        <td>${response[i].status}</td>`;

        if (role === 'manager') {

            //checks that the response is pending and the logged in user did not make the ticket
            if(response[i].authorId == id){
                newRow.style.backgroundColor = '#ffffa6';
            }else if (response[i].status == 'PENDING' && response[i].authorId != id) {
                newRow.innerHTML += `<td><button id="ApproveButton${i}">Approve</button></td>
                <td><button id="DenyButton${i}">Deny</button></td>`;

            }
        }

        document.getElementById('tablebody').appendChild(newRow);
    }
    //add event listener and style class to buttons
    for (let i = 0; i < response.length; i++) {
        if (document.getElementById(`ApproveButton${i}`)) {
            document.getElementById(`ApproveButton${i}`).addEventListener('click', updateTicket);
            document.getElementById(`ApproveButton${i}`).setAttribute('class', 'btn btn-sm btn-outline-secondary');
            document.getElementById(`ApproveButton${i}`).setAttribute('value', 'APPROVED');
            document.getElementById(`ApproveButton${i}`).setAttribute('name', response[i].reimbId);
        }
        if (document.getElementById(`DenyButton${i}`)) {
            document.getElementById(`DenyButton${i}`).addEventListener('click', updateTicket);
            document.getElementById(`DenyButton${i}`).setAttribute('class', 'btn btn-sm btn-outline-secondary');
            document.getElementById(`DenyButton${i}`).setAttribute('value', 'DENIED');
            document.getElementById(`DenyButton${i}`).setAttribute('name', response[i].reimbId);
        }
    }
    document.getElementById('typeFilter').addEventListener('change', tableFilter);
    document.getElementById('statusFilter').addEventListener('change', tableFilter);
}

function tableFilter() {

    let type = document.getElementById('typeFilter').value;
    let status = document.getElementById('statusFilter').value;


    let rows = document.getElementsByClassName('rows');
    for (let i = 0; i < rows.length; i++) {

    }
    for (let i = 0; i < rows.length; i++) {
        rows[i].hidden = false;
        if (type && rows[i].getAttribute('name') != type) {
            rows[i].hidden = true;
        }
        if (status && rows[i].getAttribute('data-status') != status) {
            rows[i].hidden = true;
        }
    }
}

async function updateTicket(e) {
    // console.log('in updateTicket');
    let btns = document.getElementsByName(e.target.name);
    for (let i = 0; i < btns.length; i++) {
        btns[i].disabled = true;
    }

    let ticketData = []
    ticketData.push('update');
    ticketData.push('0') //author is
    ticketData.push(userId);//resolver id
    ticketData.push(e.target.name); //reimbId
    ticketData.push(e.target.value); //status
    // console.log(ticketData);



//make async
    let response = await fetch('ticket', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Authorization': localStorage.getItem('jwt')
        },
        body: JSON.stringify(ticketData)
    });
    if (response.status == 200) {
        getTickets();
    }

}

/*** HELPER FUNCTIONS */
function clearBody() {
    while (document.getElementById('dashboardBody').firstChild) {
        document.getElementById('dashboardBody').removeChild(document.getElementById('dashboardBody').firstChild);
    }
}