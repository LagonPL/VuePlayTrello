<template>
<div>
	<Split style="height: 100%;" :direction="vertical">		
				<SplitArea :size="75">
					<scrolly class="horizontal-scrollbar-demo">
  					<scrolly-viewport>
					<div id="board-component">
						<div class="Overlay"
							@click.self="showCardDetails = !showCardDetails; editCardDescription = false;
										inProgressCardDescription = defaultDescription" v-show="showCardDetails">
							<div class="CardDetails">
								<div id="leftSide" class="CardDetails_LeftSide">
									<div id="cardInfoBlock" class="Block">
										<input id="cardNameInput" class="CardTitle" v-model="selectedCard.name" @blur="renameCard($event.target.value)" type="text"></input>
										<p class="CardSubtitle">w liście <u>{{ selectedCardListName }}</u></p>
										<p class="DescriptionTitle" v-if="selectedCard.description.length > 0">Opis</p>
										<p class="DescriptionContent"
											v-if="selectedCard.description.length > 0"
											v-show="!editCardDescription"
											@click="editCardDescription = !editCardDescription;
												inProgressCardDescription = selectedCard.description">
											{{ selectedCard.description }}</p>
										<p class="DescriptionContent"
											v-else
											@click="editCardDescription = !editCardDescription"
											v-show="!editCardDescription">
											<u>Edytuj opis...</u></p>
										<textarea class="DescriptionTextArea"
											v-model="inProgressCardDescription"
											v-show="editCardDescription"
											type="text"
											v-on:keyup.enter.prevent="saveNewCardDescription"></textarea>
										<div class="DescriptionActionButtonsDiv" v-show="editCardDescription">
											<button	class="Button ButtonSave"
											@click="saveNewCardDescription">Zapisz</button>
											<button class="Button ButtonCancel"
											@click="editCardDescription = !editCardDescription;
												inProgressCardDescription = defaultDescription">X</button>
										</div>
									</div>
									<div id="taskListBlock" class="Block">
										<h3 class="BlockTitle">Lista zadań</h3>
										<p class="CommentAuthor">{{ percentDone }}%</p>
										<div class="Task" v-for="(task, index) in selectedCard.tasks">
											<input type="checkbox" v-model="task.done" @click="switchTaskStatus(task)" v-if="this.status.IsLogged" >
											<span style="cursor:pointer" @click="editTask(task)">{{ task.name }}</span></input>
											<button class="Button ButtonCancel"
											@click="deleteTask(task.id, index)"
											style="display:inline" v-if="this.status.IsLogged">Usuń...</button>
										</div>
										<div class="DescriptionActionButtonsDiv">
											<button	class="Button ButtonSave"
											@click="addTask" v-if="this.status.IsLogged">Dodaj</button>
										</div>
									</div>
									<div id="activityBlock" class="Block">
										<h3 class="BlockTitle">Etykiety</h3>
										<div class="Comment" v-for="(label, index) in selectedCard.labels">
											<h3 class="Label" v-bind:style="{ color: setColor(index)}">{{ labelName(index) }}</h3>
										</div>
									</div>
									<div id="addCommentBlock" class="Block" v-if="this.status.IsLogged">
										<h3 class="BlockTitle">Dodaj komentarz</h3>
										<textarea class="CommentTextArea" v-model="newCommentText" type="text"></textarea>
										<button class="Button ButtonSave" @click="addComment">Zapisz</button>
									</div>
									<div id="activityBlock" class="Block">
										<h3 class="BlockTitle">Aktywność</h3>
										<div class="Comment" v-for="(comment, index) in selectedCard.comments">
											<h3 class="CommentAuthor">{{ comment.userMail }}</h3>
											<textarea class="CommentTextArea CommentContent" readonly>{{ comment.text }}</textarea>
											<div>
												<button class="CommentButton">Edytuj</button>
												<span style="color: #959DA1;">-</span>
												<button class="CommentButton" @click="deleteComment(comment.id, index)">Usuń</button>
											</div>
										</div>
									</div>
								</div>
								<div id="rightSide" class="CardDetails_RightSide">
									<div class="CloseOverlayButtonDiv">
										<button id="closeOverlayButton" @click="showCardDetails = !showCardDetails; editCardDescription = false">X</button>
									</div>
									<h3 class="Title">Dodaj</h3>
										<button class="ActionButton">Członkowie</button>
										<button class="ActionButton" @click="addLabel()">Etykiety</button>
										<button class="ActionButton">Lista zadań</button>
										<button class="ActionButton">Terminarz</button>
										<button class="ActionButton">Załącznik</button>
									<h3 class="Title">Działania</h3>
										<button class="ActionButton">Przenieś</button>
										<button class="ActionButton">Kopiuj</button>
										<button class="ActionButton">Subskrybuj</button>
										<button class="ActionButton" v-if="selectedCard.status == 'ARCHIVED'" @click="dearchiveCard">Przywróć</button>
										<button class="ActionButton" v-if="selectedCard.status == 'ARCHIVED'" @click="archiveCard">Usuń</button>
										<button class="ActionButton" v-else @click="archiveCard" disabled>Zarchiwizuj</button>
								</div>
							</div>
						</div>
						<div id="board">
							<div id="board-buttons">
							<button id="boardTitle" @click="renameBoard()">{{currentBoard.name}}</button>
							<button id="fav-btn" @click="deleteBoard(currentBoard.id)"><span class="glyphicon glyphicon-remove"></span></button>
							<button id="fav-btn" @click="addUser(currentBoard.id)"><span class="glyphicon glyphicon-user"></span></button>
							<button id="fav-btn" v-on:click='splitshow = !splitshow'><span class="glyphicon glyphicon-list-alt"></span></button>
							<button id="fav-btn" @click="addTeam(currentBoard.id)"><span class="glyphicon glyphicon-tower"></span></button>							
							</div>
							<div id="list" v-for="(list, listIndex) in currentBoard.listts" v-if="list.status == 'VISIBLE'">
								<p class="listName">
									{{list.name}}
									<button id="renameListButton" class="listButton" @click="renameList(list)">...</button>
									<button id="deleteListButton" class="listButton" @click="archiveList(list)">X</button>
								</p>
								<div id="card" v-for="(card, cardIndex) in list.cards" v-if="card.status == 'VISIBLE'">
									<button class="cardButton" @click="openCard(card, list)">{{card.name}}</button>
									<button id="editCardButton">...</button>
								</div>
								<button id="addCardButton" @click="addCard(list)">Dodaj kartę...</button>
							</div>
						<button id="addListButton" @click="addList(currentBoard.id)">Dodaj listę...</button>
						</div>	
					</div>	
					</scrolly-viewport>
					<scrolly-bar axis="x"></scrolly-bar>
					</scrolly>
				</SplitArea>			
		<SplitArea :size="25">
			<EventLog :eventLogText="eventlog" id="eventlog"/>
		</SplitArea>	
	</Split>
	</div>
</template>

<script> 
import axios from 'axios';
import EventLog from './EventLog.vue'
import { Scrolly, ScrollyViewport, ScrollyBar } from 'vue-scrolly';
import Vue from 'vue';

export default {
	components: {
		EventLog,
		Scrolly,
		ScrollyViewport,
		ScrollyBar
  },
	data: function () {
		return {
			labelColor: {
      			type: String,
      			default: "red"
    		},
			status: "",
			eventlog: "",
			currentBoard: this.$root.boards[this.$route.params.id - 1],
			showCardDetails: false,
			splitshow: true,
			selectedCard: {
				name: "default name",
				description: "sample description"
			},
			selectedCardListName: "default list name",
			editCardDescription: false,
			inProgressCardDescription: "Dodaj bardziej szczegółowy opis...",
			defaultDescription: "Dodaj bardziej szczegółowy opis...",
			newCommentText: "",
			defaultTaskName: "Dodaj element...",
			inProgressTaskName: "",
			newTaskName: "",
			lastColor: "",
			addUserToBoard: "Dodaj użytkownika do tablicy",
			addedLabel: false,
			addedLabelName: "",
		}
	},
	mounted: function() {
		//this.$forceUpdate();
		this.getUsername(this.$root.boards[this.$route.params.id - 1].id);
		this.getEventLog(this.$root.boards[this.$route.params.id - 1].id);
	  },
	computed: {
		percentDone: function () {
			if(this.selectedCard.tasks != null && this.selectedCard.tasks.length > 0) {
				var nrOfTasks = this.selectedCard.tasks.length;
				var nrOfTasksDone = 0;
				console.log(this.selectedCard);
				for(var i = 0; i < nrOfTasks; i++) {
					console.log ("nr taska " + i);
					console.log ("status taska " + this.selectedCard.tasks[i].done);
					if(this.selectedCard.tasks[i].done == true) {
						nrOfTasksDone++;
					}
				}
				var percent = (nrOfTasksDone / nrOfTasks) * 100;
				return Math.round(percent);
			} else {
				return 0;
			}
		}
	},
	methods: {
		getUsername: function(boardId) {
			axios.get('http://localhost:9000/api/user/role/' + boardId)
    		.then(response => {
          		console.log(response.data.body);
          		this.status = response.data.body;
       		})
        	.catch(function(error) {});
		},
		getEventLog: function(boardId) {
			axios.get('http://localhost:9000/api/boards/eventlog/' + boardId)
    		.then(response => {
				console.log(response.data.body);		  				
				for (var i = 0; i < response.data.body.length; i++) {
					this.eventlog = this.eventlog.concat(response.data.body[i].text,"\r\n");		
  				}
       		})
        	.catch(function(error) {});
    	},
		openCard: function(card, list) {
			this.selectedCard = card;
			this.selectedCardListName = list.name;
			this.showCardDetails = true;
		},
		addUser: function(boardId){
			const vm = this;
			var newMail = prompt("Podaj mail użytkownika");
			if(newMail.length != 0) {
				axios.post('http://localhost:9000/api/boards/adduser', {
				  UserViewModel: {
					parentBoard: boardId,
					mail: newMail
				  }
				})
				.then(response => {
					console.log(response);
				})
				.catch(e => {
				  console.log(e);
				})
			} else {
				alert("Nazwa nie może być pusta!");
			}
		},
		addTeam: function(boardId){
			const vm = this;
			var teamName = prompt("Podaj nazwę zespołu");
			if(teamName.length != 0) {
				axios.post('http://localhost:9000/api/team/addtoboard', {
				  teamname : teamName,
				  boardid :  boardId
				})
				.then(response => {
					console.log(response);
				})
				.catch(e => {
				  console.log(e);
				})
			} else {
				alert("Nazwa nie może być pusta!");
			}
		},
		addList: function(boardId) {
			const vm = this;
			var newName = prompt("Podaj nazwę nowej listy");
			if(newName.length != 0) {
				axios.post('http://localhost:9000/api/listt/add', {
				  ListViewModel: {
					parentBoard: boardId,
					name: newName,
					column: 1
				  }
				})
				.then(response => {
					console.log(response);
					vm.currentBoard.listts.push(response.data.body);
				})
				.catch(e => {
				  console.log(e);
				})
			} else {
				alert("Nazwa nie może być pusta!");
			}
		},
		renameList: function(list) {
			const vm = this;
			var newName = prompt("Podaj nową nazwę", list.name);
			if(newName.length != 0) {
				axios.put('http://localhost:9000/api/listt/update', {
					Listt: {
						id: list.id,
						name: newName
					}
				})
				.then(response => {
					console.log(response);
					list.name = newName;
				})
				.catch(e => {
				  console.log(e);
				})
			} else {
				alert("Nazwa nie może być pusta!");
			}
		},
		renameCard: function(newName) {
			const vm = this;
			if(newName.length != 0) {
				axios.put('http://localhost:9000/api/card/update', {
					Card: {
						id: vm.selectedCard.id,
						name: newName
					}
				})
				.then(response => {
					console.log(response);
				})
				.catch(e => {
				  console.log(e);
				})
			} else {
				alert("Nazwa nie może być pusta!");
			}
		},
		addCard: function(list) {
			const vm = this;
			var newName = prompt("Podaj nazwę nowej karty");
			if(newName.length != 0) {
				axios.post('http://localhost:9000/api/card/add', {
				  CardViewModel: {
					parentList: list.id,
					name: newName,
					row: 1,
					description: ""
				  }
				})
				.then(response => {
					console.log(response);
					list.cards.push(response.data.body);
				})
				.catch(e => {
				  console.log(e);
				})
			} else {
				alert("Nazwa nie może być pusta!");
			}
		},
		renameBoard: function() {
			const vm = this;
			var newName = prompt("Podaj nową nazwę", this.currentBoard.name);
			if(newName.length != 0) {
				axios.put('http://localhost:9000/api/boards/update', {
					Board: {
						id: vm.currentBoard.id,
						name: newName
					}
				})
				.then(response => {
					console.log(response);
					vm.currentBoard.name = newName;
					location.reload(true);
				})
				.catch(e => {
				  console.log(e);
				})
			} else {
				alert("Nazwa nie może być pusta!");
			}
		},
		archiveList: function(list) {
			const vm = this;
			axios.get('http://localhost:9000/api/listt/archive/' + list.id)
			  .then(function (response) {
				console.log(response.data);
				list.status = "ARCHIVED";
			  })
			  .catch(function (error) {
				console.log(error);
			  });
		},
		saveNewCardDescription: function() {
			this.editCardDescription = !this.editCardDescription;
			if(this.inProgressCardDescription == this.selectedCard.description) {
				console.log("bez zmian");
				return;
			} else {
				const vm = this;

				axios.put('http://localhost:9000/api/card/update', {
					Card: {
						id: vm.selectedCard.id,
						description: vm.inProgressCardDescription
					}
				})
				.then(response => {
					console.log(response);
					vm.selectedCard.description = vm.inProgressCardDescription;
					vm.inProgressCardDescription = "";
				})
				.catch(e => {
				  console.log(e);
				})
			}
		},
		archiveCard: function(newStatus) {
			const vm = this;
			axios.get('http://localhost:9000/api/card/archive/' + vm.selectedCard.id)
			  .then(function (response) {
				console.log(response.data);
				if(vm.selectedCard.status == "ARCHIVED") {
					vm.selectedCard.status = "DELETED";
					vm.showCardDetails = false;
				} else {
					vm.selectedCard.status = "ARCHIVED";
				}
			  })
			  .catch(function (error) {
				console.log(error);
			  });
		},
		dearchiveCard: function(newStatus) {
			const vm = this;
			axios.get('http://localhost:9000/api/card/dearchive/' + vm.selectedCard.id)
			  .then(function (response) {
				console.log(response.data);
				vm.selectedCard.status = "VISIBLE";
			  })
			  .catch(function (error) {
				console.log(error);
			  });
		},
		addComment: function() {
			const vm = this;
			if(vm.newCommentText.length != 0){
			axios.post('http://localhost:9000/api/comment/add', {
			  CommentViewModel: {
				text: vm.newCommentText, 
				parentCard: vm.selectedCard.id
			  }
			})
			.then(response => {
				console.log(response);
				vm.selectedCard.comments.push(response.data.body);
				vm.newCommentText = "";
			})
			.catch(e => {
			  console.log(e);
			})}
		},
		deleteComment: function(id, index) {
			const vm = this;
			axios.delete('http://localhost:9000/api/comment/delete/' + id)
			  .then(function (response) {
				console.log(response.data);
				vm.selectedCard.comments.splice(index, 1);
			  })
			  .catch(function (error) {
				console.log(error);
			  });
		},
		addTask: function() {
			const vm = this;
			var newName = prompt("Podaj nazwę zadania");
			axios.post('http://localhost:9000/api/task/add', {
			  TaskViewModel: {
				name: newName, 
				parentCard: vm.selectedCard.id
			  }
			})
			.then(response => {
				console.log(response);
				vm.selectedCard.tasks.push(response.data.body);
			})
			.catch(e => {
			  console.log(e);
			})
		},
		switchTaskStatus: function(task) {
			const vm = this;
			axios.get('http://localhost:9000/api/task/switchDone/' + task.id)
			  .then(function (response) {
				console.log(response.data);
			  })
			  .catch(function (error) {
				console.log(error);
			  });
		},
		deleteTask: function(id, index) {
			const vm = this;
			axios.delete('http://localhost:9000/api/task/delete/' + id)
			  .then(function (response) {
				console.log(response.data);
				vm.selectedCard.tasks.splice(index, 1);
			  })
			  .catch(function (error) {
				console.log(error);
			  });
		},
		editTask: function(task) {
			const vm = this;
			var newName = prompt("Podaj nową nazwę", task.name);
			if(newName.length != 0) {
				axios.put('http://localhost:9000/api/task/update', {
					Task: {
						id: task.id,
						name: newName
					}
				})
				.then(response => {
					console.log(response);
					task.name = newName;
				})
				.catch(e => {
				  console.log(e);
				})
			} else {
				alert("Nazwa nie może być pusta!");
			}
		},
		deleteBoard: function(id) {
			const vm = this;
			axios.delete('http://localhost:9000/api/boards/delete/' + id)
			  .then(function (response) {
				console.log(response.data);
				vm.selectedCard.tasks.splice(index, 1);
			  })
			  .catch(function (error) {
				console.log(error);
			  });
			 this.$router.push('/boardList');
		},
		addLabel: function() {
			const vm = this;
			var newName = prompt("Podaj nazwę etykiety");
			var labelColor = prompt("Podaj nazwę koloru");
			axios.post('http://localhost:9000/api/label/create', {
			  LabelViewModel: {
				name: newName, 
				color: labelColor,
				parentCard: vm.selectedCard.id
			  }
			})
			.then(response => {
				console.log(response);	
				this.lastColor = labelColor;	
				this.addedLabel = true;		
				this.addedLabelName = newName;
				vm.selectedCard.labels.push(response.data.body);
			})
			.catch(e => {
			  console.log(e);
			})
		},	
		setColor: function(id) {
			const vm = this;
			var color;
			var labels;
			labels = vm.selectedCard.labels.length;
			if (!this.addedLabel){				
				color = vm.selectedCard.labels[id].color;
			}	
			else {
				if (id < labels-1){
					color = vm.selectedCard.labels[id].color;
				}
				else{
					color = vm.lastColor;
				}
			}					
			return color;
		},
		labelName: function(id) {
			const vm = this;
			var name;
			var labels;
			labels = vm.selectedCard.labels.length;
			if (!this.addedLabel){				
				name = vm.selectedCard.labels[id].name;
			}	
			else {
				if (id < labels-1){
					name = vm.selectedCard.labels[id].name;
				}
				else{
					name = vm.addedLabelName;
				}
			}					
			return name;
		}	
  }	
}
</script>

<style scoped lang="scss">
.Overlay {
		display: flex;
		justify-content: center;
		align-items: flex-start;
		position: fixed;
		top: 0;
		left: 0;
		height: 100%;
		width: 100%;
		background-color: rgba(0, 0, 0, 0.5);
		z-index: 1000;
		overflow-y: scroll;
	}
	
	.CardDetails {
		background-color: #EDEFF0;
		border-radius: 3px;
		height: 80%;
		width: 80%;
		padding: 1%;
		display: table;
		margin: auto;
	}
	
	.CardDetails_LeftSide {
		float: left;
		width: 60%;
		display: table-column;
		height: 100%;
	}
		.CardDetails_LeftSide .Block {
			margin: 5px;
		}
		.CardDetails_LeftSide .BlockTitle {
			color: #4d4d4d;
			font-size: 16px;
			line-height: 24px;
			margin-bottom: 0px;
		}
		.CardDetails_LeftSide .CardTitle {
			background-color: transparent;
			width: 100%;
			min-height: 18px;
			display: block;
			border-radius: 3px;
			font-size: 18px;
			font-weight: 700;
			line-height: 30px;
			outline: none;
			border: none;
			color: #4d4d4d;
			padding-left: 5px;
		}
			.CardDetails_LeftSide .CardTitle:focus {
				background-color: white;
			}
		.CardDetails_LeftSide .CardSubtitle {
			color: #959DA1;
		}
		.CardDetails_LeftSide .DescriptionTitle {
			color: #959DA1;
		}
		.CardDetails_LeftSide .DescriptionContent {
			color: #4d4d4d;
			width: 100%;
			cursor: pointer;
		}
		.CardDetails_LeftSide .DescriptionTextArea {
			background-color: #E2E4E6;
			color: #959DA1;
			border-radius: 3px;
			border-color: silver;
			width: 100%;
			min-height: 50px;
			resize: none;
		}
		.CardDetails_LeftSide .DescriptionActionButtonsDiv {
			display: flex;
			flex-wrap: wrap;
			align-items: center;
			justify-content: flex-start;
		}
		.CardDetails_LeftSide .Button {
			background-color: #E2E4E6;
			cursor: pointer;
			display: block;
			height: 2pc;
			max-width: 300px;
			position: relative;
			user-select: none;
			white-space: nowrap;
			color: #444444;
			font-size: 14px;
		}
			.CardDetails_LeftSide .Button:hover {
				background-color: #CDD2D4;
				color: #4C4C4C;
			}
			.CardDetails_LeftSide .Button:active {
				background-color: #838C91;
				color: white;
			}
		.CardDetails_LeftSide .ButtonSave {
			background-color: #5aac44;
			color: white;
			font-size: 14px;
			font-weight: bold;
		}
			.CardDetails_LeftSide .ButtonSave:hover {
				background-color: #519839;
				color: white;
			}
			.CardDetails_LeftSide .ButtonSave:active {
				background-color: #49852E;
			}
		.CardDetails_LeftSide .ButtonCancel {
			background-color: transparent;
			color: #959DA1;
			border: none;
		}
			.CardDetails_LeftSide .ButtonCancel:hover {
				background-color: transparent;
				color: #4C4C4C;
				border: none;
			}
		.CardDetails_LeftSide .CommentTextArea {
			background-color: white;
			color: #4d4d4d;
			border-radius: 3px;
			border-color: silver;
			width: 100%;
			min-height: 50px;
			resize: none;
		}
		.CardDetails_LeftSide .Comment {
			margin-left: 5px;
		}
		.CardDetails_LeftSide .CommentAuthor {
			color: #4d4d4d;
			font-size: 14px;
			line-height: 24px;
			margin-bottom: 0px;
			margin-top: 0px;
		}
		.CardDetails_LeftSide .CommentContent {
			width: auto;
			height: auto;
			margin-bottom: 0px;
		}
		.CardDetails_LeftSide .CommentButton {
			background-color: transparent;
			color: #959DA1;
			border: none;
			text-decoration: underline;
		}
			.CardDetails_LeftSide .CommentButton:hover {
				color: #4C4C4C;
			}
		
	.CardDetails_RightSide {
		float: right;
		display: table-column;
		width: 40%;
		height: 100%;
		padding: 5px;
		box-sizing: border-box;
	}
		.CardDetails_RightSide .CloseOverlayButtonDiv {
			margin-bottom: 5px;
		}
			.CardDetails_RightSide .CloseOverlayButtonDiv #closeOverlayButton {
				float: right;
				background-color: transparent;
				font-size: 20px;
				color: #959DA1;
			}
			.CardDetails_RightSide .CloseOverlayButtonDiv #closeOverlayButton:hover {
				color: #4C4C4C;
			}
		.CardDetails_RightSide .Title {
			color: #4d4d4d;
			font-size: 16px;
			line-height: 24px; 
		}
		.CardDetails_RightSide .ActionButton {
			background-color: #E2E4E6;
			cursor: pointer;
			display: block;
			height: 2pc;
			position: relative;
			user-select: none;
			white-space: nowrap;
			color: #444444;
			width: 100%;
			text-align: left; 
			font-size: 14px;
			font-weight: bold;
			margin: 3px;
		}
		.CardDetails_RightSide .ActionButton:hover {
			background-color: #CDD2D4;
			color: #4C4C4C;
		}
	
	#fav-btn {
		background-color: transparent;
		font-size: 22px;
		padding: 0 10px;
		margin: 0;
		color: white;
		border-radius: 3px;
	}
	
	#fav-btn:hover {
		background-color: #026AA7;
	}

	p {
		margin: 0;
		padding: 3px 5px;
	}
	
	#board {
		padding: 5px;
		background-color: #0079BF;
	}
	
	#boardTitle {
	    font-size: 18px;
		font-weight: 700;
		line-height: 30px;
		color: #f6f6f6;
		padding: 0 10px;
		background-color: transparent;
		border-radius: 3px;
		margin-right: 10px;
	}
	
	#boardTitle:hover {
		background-color: #026AA7;
	}
	
	#list {
		display: inline-block;
		background-color: #E2E4E6;
		border-style: none;
		border-radius: 3px;
		padding: 5px;
		margin: 5px;
		vertical-align: top;
		height: 100%;
		width: 270px;
		white-space: nowrap;
		box-sizing: border-box;
		color: #4d4d4d;
	}
	
	.listName {
		margin-left: 0px;
		font-weight: bold;
	}
	
	#card {
		font-weight: normal;
		font-style: italic;
		background-color: white;
		border-style: none;
		border-radius: 2px;
		padding: 6px 6px 2px 8px;
		margin: 4px 2px 6px 4px;
		max-width: 300px;
		min-height: 20px;
		position: relative;
		color: #4d4d4d;
	}
	
	#card:hover {
		background-color: #EDEFF0;
	}
	
	#addCardButton {
		background-color: transparent;
		color: #838c91;
		width: 100%;
		text-align: left;
	}
	
	#addCardButton:hover {
		background-color: #CDD2D4;
		color: #818384;
		text-decoration: underline;
	}
	
	#addListButton {
		display: inline-block;
		background-color: rgba(0,0,0,.1);
		border-radius: 2px;
		padding: 5px;
		margin: 5px;
		vertical-align: top;
		height: 100%;
		width: 270px;
		white-space: nowrap;
		box-sizing: border-box;
		color: rgba(255,255,255,.7);
		text-align: left;
	}
	
	#addListButton:hover {
		background-color: rgba(0,0,0,.3);
	}

	.listButton {
		background-color: transparent;
		float: right;
	}
	
	.listButton:hover {
		background-color: #CDD2D4;
		text-decoration: underline;
	}
	
	#deleteListButton {
		color: maroon;	
	}
	
	#deleteListButton:hover {
		color: red;
	}
	
	#renameListButton {
		color: #838c91;	
	}
	
	#renameListButton:hover {
		color: #818384;
	}
	
	.cardButton {
		background-color: transparent;
		width: 100%;
		text-align: left;
		padding: 0;
		color: #4d4d4d;
	}
	
	#editCardButton {
		background-color: transparent;
		position: absolute;
		top: 0;
		right: 0;
		margin: 0;
		display: none;
		color: #4d4d4d;
	}
	
	#card:hover > #editCardButton {
		display: inline;
	}
	.horizontal-scrollbar-demo {
		width: 100%;
		height: 650px;
		bottom: 0;
  		right: 0;
	article {
		padding: 15px;
	}
	p {
		margin-top: 10px;
	}
}
// @import '../../sass/board.scss';
</style>