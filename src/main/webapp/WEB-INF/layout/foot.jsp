</div> 
           
           
           <!-- ============================================================== -->
            <!-- End Page content -->
            <!-- ============================================================== -->


        </div>
        <!-- END wrapper -->


        <!-- Vendor js -->
        <script src="assets/js/vendor.min.js"></script>

        <!-- KNOB JS -->
        <script src="assets/libs/jquery-knob/jquery.knob.min.js"></script>
        <!-- Chart JS -->
        <script src="assets/libs/chart-js/Chart.bundle.min.js"></script>

        <!-- Jvector map -->
        <script src="assets/libs/jqvmap/jquery.vmap.min.js"></script>
        <script src="assets/libs/jqvmap/jquery.vmap.usa.js"></script>
        
        <!-- Datatable js -->
        <script src="assets/libs/datatables/jquery.dataTables.min.js"></script>
        <script src="assets/libs/datatables/dataTables.bootstrap4.min.js"></script>
        <script src="assets/libs/datatables/dataTables.responsive.min.js"></script>
        <script src="assets/libs/datatables/responsive.bootstrap4.min.js"></script>
        
        <!-- Datatables initialisation -->
        
        <c:if test="${table != null}">
        	<script src="assets/js/pages/${table }.init.js"></script>
        
        </c:if>
        
        <!-- App js -->
        <script src="assets/js/app.min.js"></script>
	
	</body>
</html>