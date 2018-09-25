var IndexViewer = function() {
}

IndexViewer.prototype = {
	init : function() {
		$.ajax({
			url : "/seatsStatus",
			type : 'GET',
			dataType : 'json',
			success : this.initialCallBack.bind(this)
		});
	},
	update : function() {
		$.ajax({
			url : "/seatsStatus",
			type : 'GET',
			dataType : 'json',
			success : this.statusUpdateCallBack
		});
	},
	initialCallBack : function(response) {
		let row = response.row;
		let column = response.column;
		let seats = response.seats;
		for (let i = 0; i < row; i++) {
			let seatRow = $("<ul>");
			for (let j = 0; j < column; j++) {
				let seatColumn = $("<li>").addClass(
						"" + seats[i * column + j].status).attr("id",
						"seat" + (i * column + j));
				seatRow.append(seatColumn);
			}
			$("#seatsDiv").append(seatRow);
		}
		setInterval(this.update.bind(this), 1000);
		setInterval(this.holdRequest.bind(this), 2000);
	},
	statusUpdateCallBack : function(response) {
		let row = response.row;
		let column = response.column;
		let seats = response.seats;
		for (let i = 0; i < row; i++) {
			for (let j = 0; j < column; j++) {
				$("#seat" + (i * column + j)).removeClass().addClass(
						seats[i * column + j].status);
			}
		}
	},
	holdRequest : function() {
		$.ajax({
			url : "/hold/"+new Date().getTime()+"@mail.com/"+ (Math.floor(Math.random() * 10) + 1)  ,
			type : 'GET',
			dataType : 'json',
			success : this.holdRequestCallBack.bind(this)
		});
	},
	holdRequestCallBack : function(response){
		setTimeout(this.reserveRequest.bind(this), (Math.floor(Math.random() * 25) + 1) * 1000, response);
	},
	reserveRequest: function(response){
		$.ajax({
			url : "/reserve/"+response.customerEmail+"/"+ response.seatHodeId ,
			type : 'GET',
			dataType : 'json',
			success : function(){},
			error : function(){}
		});
	}
}

$(document).ready(function() {
	let viewer = new IndexViewer();
	viewer.init();
});