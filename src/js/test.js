var admin = require("firebase");
var firebaseConfig = {
  apiKey: "AIzaSyC4sVns739b-xuAGTswsT5mbNskUNRFj7k",
  authDomain: "blockchain-tsec.firebaseapp.com",
  databaseURL: "https://blockchain-tsec.firebaseio.com",
  projectId: "blockchain-tsec",
  storageBucket: "",
  messagingSenderId: "973444537977",
  appId: "1:973444537977:web:125bb5c293430627c7bea8"
};

admin.initializeApp(firebaseConfig);



var db=admin.database();
var ref=db.ref("Users");
var jsonObject
ref.on("value",function(snapshot){
   jsonObject=snapshot.val();
   console.log(jsonObject)
},function(errorObject) {
  console.log("bshabsj");
});
// .on('click', App.insertValues)

// function insertValues(e) {
    
//           //vcount++
//           var id=e.target.id
//           var vcount=e.target.vcount;
//           vcount=vcount+1;
//           db.ref("Users/"+id+"/experience/"+e.target.count+"/verified").set({
//             verified: true,

//           })
//           db.ref("Users/"+id).set({
//             vcount: vcount,

//           })
//           if(vcount==e.target.tcount)
//           {
//           //verified++
//           //check
//           const contact=e.target.contact
//           const email=e.target.email
//           const firstName=e.target.firstName
//           const lastName=e.target.lastName
//           const cgpa=e.target.cgpa
//           const technicalSkills=e.target.technicalSkills
//           const experience=e.target.experience

//           console.log(experience)
//   await App.hrProcess.addSeeker(email,firstName,lastName,contact,technicalSkills,cgpa,JSON.stringify(experience))
//           }
//     window.location.reload()
//   }
  // module.exports.insertValues=insertValues;
  // module.exports.db=db
  // module.exports.ref=ref