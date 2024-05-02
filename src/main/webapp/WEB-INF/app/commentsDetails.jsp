<c:if test="${errorMsg == null }">
	<div class="row">
		<div class="col-12">
			<div class="card-box">
				<h1 class="header-title">
					<c:out value="${game.title }" />
				</h1>
				<h5><c:out value="${not empty approvedCommentsList || not empty NOTapprovedCommentsList ? 'Modération des commentaires' : 'Pas de commentaire' }" /></h5>
				
				<c:if test="${not empty NOTapprovedCommentsList }">
				<p class="sub-header">Vous pouvez <code>modifier</code> et <code>approuver</code> le commentaire. Vous pouvez également <code>refuser</code> le commentaire. Le commentaire sera effacé mais la note sera cependant conservée.</p>
				<c:forEach items="${NOTapprovedCommentsList }" var="comment">
	
					<form class="form-horizontal" method="post" action="commentsDetails?game_id=${comment.gameId }&user_id=${comment.userId }" id="form-${comment.userId }">
					
					  <fieldset>
	    				<legend><h5>Commentaire de <span style="color:violet"><c:out value="${comment.userNickname }"/></span> le <fmt:formatDate value="${comment.uploaded }" pattern="EEEE dd MMMM yyyy HH:mm" /></h5></legend>
						
						<div class="form-group row">
	                        <label class="col-sm-2 col-form-label" for="example-textarea">Contenu du commentaire</label>
	                        <div class="col-sm-10">
	                            <textarea class="form-control" id="example-textarea" rows="5" name="content"><c:out value="${comment.content }"/></textarea>
	                        </div>
	                    </div>
	                    
	                    <div class="form-group row">
	                        <label class="col-sm-2 col-form-label">Note :</label>
	                        <div class="col-sm-10">
	                            <input type="text" class="form-control" readonly="" value="${comment.score }">
	                        </div>
	                    </div>
		
						<div class="form-group row mb-0">
							<div class="col-sm-8 offset-sm-4">
								<button type="submit" form="form-${comment.userId }"
									class="btn btn-primary waves-effect waves-light mr-1" name="action" value="approve" style="background-color:green!important">
									Approuver le commentaire de <c:out value="${comment.userNickname }"/>
								</button>
								<button type="submit" form="form-${comment.userId }"
									class="btn btn-primary waves-effect waves-light mr-1" name="action" value="refuse" style="background-color:red!important">
									Refuser le commentaire de <c:out value="${comment.userNickname }"/>
								</button>
							</div>
						</div>
						</fieldset>
						<hr style="border:1px solid grey!important">
					</form>
				
				</c:forEach>
				
				</c:if>
	
				<c:if test="${not empty approvedCommentsList }">
				
				<c:forEach items="${approvedCommentsList }" var="comment">
	
					<form class="form-horizontal" method="post" action="commentsDetails?game_id=${comment.gameId }&user_id=${comment.userId }" id="form-${comment.userId }">
					  <fieldset>
	    				<legend><h5>Commentaire de <span style="color:violet"><c:out value="${comment.userNickname }"/></span> le <fmt:formatDate value="${comment.uploaded }" pattern="EEEE dd MMMM yyyy HH:mm" /></h5></legend>
						
						<div class="form-group row">
	                        <label class="col-sm-2 col-form-label" for="example-textarea">Contenu du commentaire</label>
	                        <div class="col-sm-10">
	                            <textarea class="form-control" id="example-textarea" rows="5" name="content"><c:out value="${comment.content }"/></textarea>
	                        </div>
	                    </div>
	                    
	                    <div class="form-group row">
	                        <label class="col-sm-2 col-form-label">Note :</label>
	                        <div class="col-sm-10">
	                            <input type="text" class="form-control" readonly="" value="${comment.score }">
	                        </div>
	                    </div>
		
						<div class="form-group row mb-0">
							<div class="col-sm-8 offset-sm-4">
								<button type="submit" form="form-${comment.userId }"
									class="btn btn-primary waves-effect waves-light mr-1"  name="action" value="modify">
									Modifier le commentaire de <c:out value="${comment.userNickname }"/>
								</button>
							</div>
						</div>
						</fieldset>
						<hr style="border:1px solid grey!important">
					</form>
				
				</c:forEach>
				
				</c:if>
	
			</div>
			<!-- end card-box -->
		</div>
		<!-- end col -->
	</div>
</c:if>


