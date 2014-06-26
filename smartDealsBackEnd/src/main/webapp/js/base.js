
var deals = deals || {};

/**
 * Client ID of the application (from the APIs Console).
 * @type {string}
 */
deals.CLIENT_ID = '814977918471-1aqm4d7qirgcc1hlh9shmau46bjp39rb.apps.googleusercontent.com';

/**
 * Scopes used by the application.
 * @type {string}
 */
deals.SCOPES = 'https://www.googleapis.com/auth/userinfo.email';

/**
 * Whether or not the user is signed in.
 * @type {boolean}
 */
deals.signedIn = false;

/**
 * Loads the application UI after the user has completed auth.
 */
deals.userAuthed = function() {
  var request = gapi.client.oauth2.userinfo.get().execute(function(resp) {
    if (!resp.code) {
      deals.signedIn = true;
      document.getElementById('signinButton').innerHTML = 'Sign out';
    }
  });
};

/**
 * Handles the auth flow, with the given value for immediate mode.
 * @param {boolean} mode Whether or not to use immediate mode.
 * @param {Function} callback Callback to call on completion.
 */
deals.signin = function(mode, callback) {
  gapi.auth.authorize({client_id: deals.CLIENT_ID,
      scope: deals.SCOPES, immediate: mode},
      callback);
};


/**
 * Presents the user with the authorization popup.
 */
deals.auth = function() {
  if (!deals.signedIn) {
    deals.signin(false,
        deals.userAuthed);
  } else {
    deals.signedIn = false;
    document.getElementById('signinButton').innerHTML = 'Sign in';
  }
};

/**
 * Prints a deal to the deal log.
 * param {Object} greeting Greeting to print.
 */
deals.print = function(deal) {
  var element = document.createElement('div');
  element.classList.add('row');
  element.innerHTML = deal.nomDeal + deal.nomMarchand ;
  document.getElementById('outputLog').appendChild(element);
};


deals.getDeal = function(name) {
  gapi.client.smartdeals.getDealByName({'nom' : name}).execute(
      function(resp) {
        if (!resp.code) {
          deals.print(resp);
        } else {
          window.alert(resp.message);
        }
      });
};


deals.saveDeal = function(name, marchand, desc, prix) {
	  gapi.client.smartdeals.insertDeal(
			  { 'nomDeal' : name,
				'nomMarchand' : marchand,
			  	'desciprtionDeal' : desc,
			  	'prix' : prix,
			  	'prixGeneralementConstate' : prix
			  	}).execute(
	      function(resp) {
	        if (!resp.code) {
	          deals.print("succes");
	        } else {
	          window.alert(resp.message);
	        }
	      });
	};
	
deals.deleteDeal = function(name){
	gapi.client.smartdeals.deleteDeal({'nom' : name}).execute(
	function(resp){
		if(!resp.code){
			deals.print("succes");
		}else{
			windows.alert(resp.message);
		}
	}		
	);
};

deals.listDeals = function() {
  gapi.client.smartdeals.listDeals().execute(
      function(resp) {
        if (!resp.code) {
          resp.items = resp.items || [];
          for (var i = 0; i < resp.items.length; i++) {
            deals.print(resp.items[i]);
          }
        }
      });
};

/**
  * Enables the button callbacks in the UI.
  */
deals.enableButtons = function() {
	document.getElementById('getDeal').onclick = function() {
		deals.getDeal(
	    document.getElementById('name').value);
	 }

	 document.getElementById('insertDeal').onclick = function() {
	    deals.saveDeal(
	       document.getElementById('nomDeal').value,
	       document.getElementById('desciprtionDeal').value,
	       document.getElementById('nomMarchand').value,
	       document.getElementById('prix').value);
	 }
	 
	 document.getElementById('listDeals').onclick = function(){
		    deals.listDeals();
		  }
	  
	 document.getElementById('deleteDeal').onclick = function(){
	    deals.deleteDeal(
		 document.getElementById('nameDealToDelete').value
			  );
	  }
	  

};

	/**
	 * Initializes the application.
	 * @param {string} apiRoot Root of the API's path.
	 */
	deals.init = function(apiRoot) {
	  // Loads the OAuth and smartdeals APIs asynchronously, and triggers login
	  // when they have completed.
	  var apisToLoad;
	  var callback = function() {
	    if (--apisToLoad == 0) {
	      deals.enableButtons();
	      deals.signin(true,deals.userAuthed);
	    }
	  }

	  apisToLoad = 2; // must match number of calls to gapi.client.load()
	  gapi.client.load('smartdeals', 'v1', callback, apiRoot);
	  gapi.client.load('oauth2', 'v2', callback);
	};
  
