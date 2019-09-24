
// let abc=require('./a.js')
//var abc=require('./a.js')
App = {
 
  loading: false,
    contracts: {},

    
    load: async () => {

      await App.loadWeb3()
      await App.loadAccount()
      await App.loadContract()
      await App.render()
    },
    loadWeb3: async () => {
      if (typeof web3 !== 'undefined') {
        App.web3Provider = web3.currentProvider
        web3 = new Web3(web3.currentProvider)
      } else {
        window.alert("Please connect to Metamask.")
      }
      // Modern dapp browsers...
      if (window.ethereum) {
        window.web3 = new Web3(ethereum)
        try {
          // Request account access if needed
          await ethereum.enable()
          // Acccounts now exposed
          web3.eth.sendTransaction({/* ... */})
        } catch (error) {
          // User denied account access...
        }
      }
      // Legacy dapp browsers...
      else if (window.web3) {
        App.web3Provider = web3.currentProvider
        window.web3 = new Web3(web3.currentProvider)
        // Acccounts always exposed
        web3.eth.sendTransaction({/* ... */})
      }
      // Non-dapp browsers...
      else {
        console.log('Non-Ethereum browser detected. You should consider trying MetaMask!')
      }
    },loadAccount: async () => {
      // Set the current blockchain account
      App.account = web3.eth.accounts[0]
    },
  
    loadContract: async () => {
      // Create a JavaScript version of the smart contract
      const hrProcess = await $.getJSON('HRProcess.json')
      App.contracts.HRProcess = TruffleContract(hrProcess)
      App.contracts.HRProcess.setProvider(App.web3Provider)
  
      // Hydrate the smart contract with values from the blockchain
      App.hrProcess = await App.contracts.HRProcess.deployed()
    },
    render: async () => {

    if(App.loading){
      return;
    }
    App.setLoading(true);

    await App.renderTasks()
    App.setLoading(false);
  },
  renderTasks: async() =>{
    const jobSeekerCount=await App.hrProcess.seekerCount();
    const $seekerTemplate = $('.seekerTemp')
  
      // Render out each task with a new task template
      for (var i = 1; i <= jobSeekerCount; i++) {
        // Fetch the task data from the blockchain
        const task = await App.hrProcess.allJobSeekers(i)
        const taskId = task[0].toNumber()
        var taskCon = task[2]
        const taskContent="<a href=\"https://www.google.com\">"+taskCon+"</a>"
        const taskContent1 = task[4]

        const taskContent2 = task[6]

        console.log(taskContent)
  
        // Create the html for the task
        const $newSeekerTemplate = $seekerTemplate.clone()
        $newSeekerTemplate.find('.content').html(taskContent+" "+taskContent1+" "+taskContent2)
        // $newTaskTemplate.find('input')
        //                 .prop('name', taskId)
        //                 .prop('checked', taskCompleted)
        //                 .on('click', App.toggleCompleted)
  
        // Put the task in the correct list
       
        $('#seekerList').append($newSeekerTemplate)
        
      
  
        // Show the task
        $newSeekerTemplate.show()
      }
    // const $seekerCount = $('#')
    // console.log(jobSeekerCount);
    // var jobSeekersList = $("#jobSeekersList");
    // jobSeekersList.text("helo")
    // jobSeekersList.empty();

    // for(var i=1;i<jobSeekerCount;i++){
    //   const jobSeeker=await App.hrProcess.allJobSeekers(i);
    //   var id=jobSeeker[0];
    //   var name=jobSeeker[1];
    //   console.log(id)
    //   var jobSeekerTemplate = "<tr><th>" + id + "</th><td>" + name + "</td></tr>";
    //   jobSeekersList.append(jobSeekerTemplate);
    // }
  }, setLoading: (boolean) => {
    App.loading = boolean
    const loader = $('#loader')
    const content = $('#content')
    if (boolean) {
      loader.show()
      content.hide()
    } else {
      loader.hide()
      content.show()
    }
  },
  insertdata: async () => {

    var myCompany="B"
    App.setLoading(true) 
    var jsonObject={
  "E8eAd2EAg0hJhZntynNQ5Cnx1ov1" : {
    "cgpa" : "10",
    "contact" : "99999",
    "email" : "b@b.com",
    "experience" : [ {
      "companyName" : "A",
      "durationInMonths" : "1",
      "position" : "P",
      "salary" : "123",
      "verified" : true
    }, {
      "companyName" : "B",
      "durationInMonths" : "1",
      "position" : "P",
      "salary" : "1",
      "verified" : false
    } ],
    "firstName" : "Somil",
    "hasUploaded" : false,
    "lastName" : "Jain",
    "tcount" : 2,
    "technicalSkills" : "Java,Python",
    "vcount" : 0
  },
  "zZSAj8zK6zY9XJfdvrDBVAr6GD62" : {
    "CGPA" : "8.7",
    "contact" : "143768",
    "email" : "email@email.com",
    "experience" : [ {
      "companyName" : "Bbbb",
      "durationInMonths" : "565",
      "position" : "Manager",
      "salary" : "5686",
      "verified" : false
    }, {
      "companyName" : "Cbh",
      "durationInMonths" : "898",
      "position" : "xjdj",
      "salary" : "898",
      "verified" : false
    } ],
    "firstName" : "aman",
    "hasUploaded" : true,
    "lastName" : "ejej",
    "tcount" : 2,
    "technicalSkills" : "Jav",
    "vcount" : 0
  }
}

    var id,contact,email,firstName,lastName,cgpa,technicalSkills,durationInMonths,salary,position,tcount,vcount
    
for (var key in jsonObject) {
  if (jsonObject.hasOwnProperty(key)) {
    var val = jsonObject[key];
    id=val
    var ob=val.experience  
    var count=-1 
    for(var key2 in ob)
    {
      count=count+1;
      if (ob.hasOwnProperty(key2)) {
        var val2 = ob[key2];
        console.log(val2.companyName)
        if(val2.companyName==myCompany)
        {
          console.log(val2.companyName)
          contact=val.contact
          email=val.email
          firstName=val.firstName
          lastName=val.lastName
          cgpa=val.cgpa
          tcount=val.tcount
          vcount=val.vcount
          technicalSkills=val.technicalSkills
          durationInMonths=val2.durationInMonths
          salary=val2.salary
          position=val2.position
          console.log(position)
          const $pendingTemplate = $('.pendingTemp')
          const $newPendingTemplate = $pendingTemplate.clone()
        $newPendingTemplate.find('.content1').html(firstName+" "+lastName+" ")
        $newPendingTemplate.find('#btn1')
                         .prop('firstName', firstName)
                         .prop('lastName', lastName)
                         .prop('email', email)
                         .prop('contact', contact)
                         .prop('cgpa', cgpa)
                         .prop('technicalSkills', technicalSkills)
                         .prop('experience', ob)
                         .prop('id', id)
                         .prop('tcount', tcount)
                          .prop('vcount', vcount)
                          .prop('count', count)
                         .on('click',App.insertValues )
        $newPendingTemplate.find('.content2').html(contact+" "+email+" ")
        $newPendingTemplate.find('.content3').html(cgpa+"cgpa Skills:"+technicalSkills+" ")
        $newPendingTemplate.find('.content4').html("Post: "+position+" Salary:"+salary+" Duration:"+durationInMonths)
        $('#pendingList').append($newPendingTemplate)
        $newPendingTemplate.show()
        //App.setLoading(true) 
        }
      }
    }  
  }
}
    
    App.setLoading(false)
  },

  insertValues: async (e) => {
    App.setLoading(true)
    
          var id=e.target.id
          var vcount=e.target.vcount;
          vcount=vcount+1;

          //
          //verified++
          //check
          const contact=e.target.contact
          const email=e.target.email
          const firstName=e.target.firstName
          const lastName=e.target.lastName
          const cgpa=e.target.cgpa
          const technicalSkills=e.target.technicalSkills
          const experience=e.target.experience

          console.log(experience)
  await App.hrProcess.addSeeker(email,firstName,lastName,contact,technicalSkills,cgpa,JSON.stringify(experience))
         // }
    window.location.reload()
  },
};
    

$(() => {
  $(window).load(() => {
    App.load()

  })
})