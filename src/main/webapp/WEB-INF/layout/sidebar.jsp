<div class="left-side-menu">

	<div class="slimScrollDiv mm-active"
		style="position: relative; overflow: hidden; width: auto; height: 853px;">
		<div class="slimscroll-menu mm-show"
			style="overflow: hidden; width: auto; height: 853px;">

			<!--- Sidemenu -->
			<div id="sidebar-menu" class="mm-active">

				<ul class="metismenu mm-show" id="side-menu">

					<li class="menu-title">Navigation</li>

					<li class="mm-active"><a href="dashboard" class="active">
							<i class="fa-solid fa-gauge-high"></i><span> Dashboard </span>
					</a></li>

					<li><a href="javascript: void(0);"><i class="fa-solid fa-gamepad"></i><span> Jeux </span> <span
							class="menu-arrow"></span>
					</a>
						<ul class="nav-second-level mm-collapse" aria-expanded="false">
							<li><a href="games?list"><i class="fa-solid fa-shop"></i>&nbsp;&nbsp;En vente</a></li>
							<li><a href="games?waiting"><i class="fa-solid fa-hourglass-half"></i>&nbsp;&nbsp;En attente</a></li>
							<li><a href="games?archived"><i class="fa-solid fa-box-archive"></i>&nbsp;&nbsp;Archivés</a></li>
							<li><a href="gameDetails?id=0"><i class="fa-solid fa-plus"></i>&nbsp;&nbsp;Ajouter</a></li>
						</ul>
					</li>
					
					<li class="mm-active"><a href="users" class="active">
							<i class="fa-solid fa-user"></i><span> Utilisateurs </span>
					</a></li>
					
					<li class="mm-active"><a href="orders" class="active">
							<i class="fa-solid fa-cash-register"></i><span> Commandes </span>
					</a></li>
					
					<li class="mm-active"><a href="comments" class="active">
							<i class="fa-regular fa-comment-dots"></i> <span> Commentaires </span> <span class="badge badge-danger float-center" style="${notifs > 0 ? '' : 'background-color:green!important'}"><c:out value="${notifs } "></c:out></span>
					</a></li>

					
					<li><a href="javascript: void(0);"><i class="fa-solid fa-people-group"></i> <span> Développeurs </span>
							<span class="menu-arrow"></span>
					</a>
						<ul class="nav-second-level mm-collapse" aria-expanded="false">
							<li><a href="developers?list"><i class="fa-solid fa-circle-dot"></i>&nbsp;&nbsp;Lister</a></li>
							<li><a href="developerDetails"><i class="fa-solid fa-plus"></i>&nbsp;&nbsp;Ajouter</a></li>
						</ul>
					</li>
					
					<li><a href="javascript: void(0);"> <i class="fa-solid fa-desktop"></i><span> Plateformes </span><span
							class="menu-arrow"></span>
					</a>
						<ul class="nav-second-level mm-collapse" aria-expanded="false">
							<li><a href="platforms?list"><i class="fa-solid fa-circle-dot"></i>&nbsp;&nbsp;Lister</a></li>
							<li><a href="platformDetails"><i class="fa-solid fa-plus"></i>&nbsp;&nbsp;Ajouter</a></li>
						</ul>
					</li>
					
					<li><a href="javascript: void(0);"><i class="fa-solid fa-bolt"></i><span> Genres </span><span
							class="menu-arrow"></span>
					</a>
						<ul class="nav-second-level mm-collapse" aria-expanded="false">
							<li><a href="genres?list"><i class="fa-solid fa-circle-dot"></i>&nbsp;&nbsp;Lister</a></li>
							<li><a href="genreDetails"><i class="fa-solid fa-plus"></i>&nbsp;&nbsp;Ajouter</a></li>
						</ul>
					</li>
					
					<li><a href="javascript: void(0);"> <i class="fa-solid fa-dice-five"></i><span> Modes de jeu </span><span
							class="menu-arrow"></span>
					</a>
						<ul class="nav-second-level mm-collapse" aria-expanded="false">
							<li><a href="modes?list"><i class="fa-solid fa-circle-dot"></i>&nbsp;&nbsp;Lister</a></li>
							<li><a href="modeDetails"><i class="fa-solid fa-plus"></i>&nbsp;&nbsp;Ajouter</a></li>
						</ul>
					</li>
					
					<li><a href="javascript: void(0);"> <i class="fa-solid fa-chess-king"></i><span> Admins </span> <span
							class="menu-arrow"></span>
					</a>
						<ul class="nav-second-level mm-collapse" aria-expanded="false">
							<li><a href="admins?list"><i class="fa-solid fa-circle-dot"></i>&nbsp;&nbsp;Lister</a></li>
							<li><a href="adminDetails"><i class="fa-solid fa-plus"></i>&nbsp;&nbsp;Ajouter/Modifier</a></li>
						</ul>
					</li>

				</ul>

			</div>
			<!-- End Sidebar -->

			<div class="clearfix"></div>

		</div>
		<div class="slimScrollBar"
			style="background: rgb(158, 165, 171); width: 8px; position: absolute; top: 0px; opacity: 0.4; display: none; border-radius: 7px; z-index: 99; right: 1px; height: 853px;"></div>
		<div class="slimScrollRail"
			style="width: 8px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: rgb(51, 51, 51); opacity: 0.2; z-index: 90; right: 1px;"></div>
	</div>
	<!-- Sidebar -left -->

</div>