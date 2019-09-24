pragma solidity >=0.4.21 <0.6.0;

contract HRProcess{
    struct User {
        uint id;
        string emailID;
        string firstName;
        string lastName;
        string phoneNumber;
        string techSkills;
        string cgpa;
        string experience;
    }

    mapping(address => bool) public uploadedJobSeekers;

    mapping(uint => User) public allJobSeekers;

    uint public seekerCount;

    event uploadedResume(
        uint indexed _seekerId
    );

    constructor() public{
        addSeeker("email","name","sn","1234567890","nadaa","10","nada");
        addSeeker("email2","name2","sn","1234567890","nadaa222","10","nada");
    }

    function addSeeker(string memory _email,string memory _first,string memory _second,string memory _phone,string memory _tech,string memory _cgpa,string memory _exp) public{
        seekerCount++;
        allJobSeekers[seekerCount]=User(seekerCount,_email,_first,_second,_phone,_tech,_cgpa,_exp);
    }

    function upload(uint _seekerId) public {
        require(!uploadedJobSeekers[msg.sender]);

        require(_seekerId > 0 && _seekerId <= seekerCount);

        uploadedJobSeekers[msg.sender] = true;

        emit uploadedResume(_seekerId);

    }
}
  