// defines response codes from the server

Ext.define('homeTracking.utils.globalMethod', {
    singleton : true,
    
    config : {
		//backStackDepth:0,
    },
    backStackDepth:0,
    updateBackStackDepth: function(increment) {
		
		this.backStackDepth += increment;
        document.removeEventListener("backbutton", this.onBack, false);
        if (this.backStackDepth > 0) {
          document.addEventListener("backbutton", this.onBack, false)
        }
        
        //this.fireEvent('backStackDepth', this.backStackDepth);
		console.log('global val'+this.backStackDepth);
		
	},
	onBack: function() {
		
        history.back();
        //prevent back button popping main view directly..
        return false;
    },
    pushState:function(){
		//console.log('push call');
		//push state so back button will work..
        //history.pushState(null, "");
        this.updateBackStackDepth(+1);
	},
	popState:function(){
		//console.log('pop call');
		//retrieve element from the history list..
        this.updateBackStackDepth(-1);
	},
	initBackStackDepth:function(){
		//console.log('init call');
		
		this.backStackDepth = 0;
		
	},
    constructor: function(config) {
        this.initConfig(config);
    }
})

