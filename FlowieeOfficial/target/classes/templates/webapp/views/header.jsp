<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Flowiee Official | Dashboard</title>
  <th:block th:fragment="stylesheets"><!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet"
      href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome -->
    <link rel="stylesheet" th:href="@{/plugins/fontawesome-free/css/all.min.css}">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Tempusdominus Bootstrap 4 -->
    <link rel="stylesheet" th:href="@{/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css}">
    <!-- iCheck -->
    <link rel="stylesheet" th:href="@{/plugins/icheck-bootstrap/icheck-bootstrap.min.css}">
    <!-- JQVMap -->
    <link rel="stylesheet" th:href="@{/plugins/jqvmap/jqvmap.min.css}">
    <!-- Theme style -->
    <link rel="stylesheet" th:href="@{/dist/css/adminlte.min.css}">
    <!-- overlayScrollbars -->
    <link rel="stylesheet" th:href="@{/plugins/overlayScrollbars/css/OverlayScrollbars.min.css}">
    <!-- Daterange picker -->
    <link rel="stylesheet" th:href="@{/plugins/daterangepicker/daterangepicker.css}">
    <!-- summernote -->
    <link rel="stylesheet" th:href="@{/plugins/summernote/summernote-bs4.min.css}">
    <!-- DataTables -->
    <link rel="stylesheet" th:href="@{/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css}">
    <link rel="stylesheet" th:href="@{/plugins/datatables-responsive/css/responsive.bootstrap4.min.css}">
    <link rel="stylesheet" th:href="@{/plugins/datatables-buttons/css/buttons.bootstrap4.min.css}">
    <!-- SimpleMDE -->
    <link rel="stylesheet" th:href="@{/plugins/simplemde/simplemde.min.css}">
    <!-- dropzonejs -->
    <link rel="stylesheet" th:href="@{/plugins/dropzone/min/dropzone.min.css}">
    <!-- Ekko Lightbox -->
    <link rel="stylesheet" th:href="@{/plugins/ekko-lightbox/ekko-lightbox.css}">
  </th:block>
</head>

<body class="hold-transition sidebar-mini layout-fixed">
  <div th:fragment="header">
    <!-- Navbar -->
    <nav class="main-header navbar navbar-expand navbar-white navbar-light">
      <!-- Left navbar links -->
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
        </li>
      </ul>

      <!-- Right navbar links -->
      <ul class="navbar-nav ml-auto">

        <!-- Notifications Dropdown Menu -->
        <li class="nav-item dropdown">
          <a class="nav-link" data-toggle="dropdown" href="#">
            <i class="far fa-bell"></i>
            <span class="badge badge-warning navbar-badge">15</span>
          </a>
          <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
            <span class="dropdown-item dropdown-header">15 Notifications</span>
            <div class="dropdown-divider"></div>
            <a href="#" class="dropdown-item">
              <i class="fas fa-envelope mr-2"></i> 4 new messages
              <span class="float-right text-muted text-sm">3 mins</span>
            </a>
            <div class="dropdown-divider"></div>
            <a href="#" class="dropdown-item">
              <i class="fas fa-users mr-2"></i> 8 friend requests
              <span class="float-right text-muted text-sm">12 hours</span>
            </a>
            <div class="dropdown-divider"></div>
            <a href="#" class="dropdown-item">
              <i class="fas fa-file mr-2"></i> 3 new reports
              <span class="float-right text-muted text-sm">2 days</span>
            </a>
            <div class="dropdown-divider"></div>
            <a href="#" class="dropdown-item dropdown-footer">See All Notifications</a>
          </div>
        </li>
        <li class="nav-item">
          <a class="nav-link" data-widget="fullscreen" href="#" role="button">
            <i class="fas fa-expand-arrows-alt"></i>
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" data-widget="control-sidebar" data-controlsidebar-slide="true" href="#" role="button">
            <i class="fas fa-th-large"></i>
          </a>
        </li>
      </ul>
    </nav>
  </div>
  <div th:fragment="scripts">
    <!-- jQuery -->
    <script th:src="@{/plugins/jquery/jquery.min.js}"></script>
    <!-- jQuery UI 1.11.4 -->
    <script th:src="@{/plugins/jquery-ui/jquery-ui.min.js}"></script>
    <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
    <script>
      $.widget.bridge('uibutton', $.ui.button)
    </script>
    <!-- Bootstrap 4 -->
    <script th:src="@{/plugins/bootstrap/js/bootstrap.bundle.min.js}"></script>
    <!-- ChartJS -->
    <script th:src="@{/plugins/chart.js/Chart.min.js}"></script>
    <!-- Sparkline -->
    <script th:src="@{/plugins/sparklines/sparkline.js}"></script>
    <!-- JQVMap -->
    <script th:src="@{/plugins/jqvmap/jquery.vmap.min.js}"></script>
    <script th:src="@{/plugins/jqvmap/maps/jquery.vmap.usa.js}"></script>
    <!-- jQuery Knob Chart -->
    <script th:src="@{/plugins/jquery-knob/jquery.knob.min.js}"></script>
    <!-- daterangepicker -->
    <script th:src="@{/plugins/moment/moment.min.js}"></script>
    <script th:src="@{/plugins/daterangepicker/daterangepicker.js}"></script>
    <!-- Tempusdominus Bootstrap 4 -->
    <script th:src="@{/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js}"></script>
    <!-- Summernote -->
    <script th:src="@{/plugins/summernote/summernote-bs4.min.js}"></script>
    <!-- overlayScrollbars -->
    <script th:src="@{/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js}"></script>
    <!-- AdminLTE App -->
    <script th:src="@{/dist/js/adminlte.js}"></script>
    <!-- AdminLTE for demo purposes -->
    <script th:src="@{/dist/js/demo.js}"></script>
    <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
    <script th:src="@{/dist/js/pages/dashboard.js}"></script>
    <link rel="stylesheet" href="https://kit.fontawesome.com/68eba0f5f3.css" crossorigin="anonymous">
    <!--Icon-->
    <script src="https://kit.fontawesome.com/68eba0f5f3.js" crossorigin="anonymous"></script>
    <!-- DataTables  & Plugins -->
    <script th:src="@{/plugins/datatables/jquery.dataTables.min.js}"></script>
    <script th:src="@{/plugins/datatables-bs4/js/dataTables.bootstrap4.min.js}"></script>
    <script th:src="@{/plugins/datatables-responsive/js/dataTables.responsive.min.js}"></script>
    <script th:src="@{/plugins/datatables-responsive/js/responsive.bootstrap4.min.js}"></script>
    <script th:src="@{/plugins/datatables-buttons/js/dataTables.buttons.min.js}"></script>
    <script th:src="@{/plugins/datatables-buttons/js/buttons.bootstrap4.min.js}"></script>
    <script th:src="@{/plugins/jszip/jszip.min.js}"></script>
    <script th:src="@{/plugins/pdfmake/pdfmake.min.js}"></script>
    <script th:src="@{/plugins/pdfmake/vfs_fonts.js}"></script>
    <script th:src="@{/plugins/datatables-buttons/js/buttons.html5.min.js}"></script>
    <script th:src="@{/plugins/datatables-buttons/js/buttons.print.min.js}"></script>
    <script th:src="@{/plugins/datatables-buttons/js/buttons.colVis.min.js}"></script>
    <!-- Summernote -->
    <script th:src="@{/plugins/summernote/summernote-bs4.min.js}"></script>
    <!-- dropzonejs -->
    <script th:src="@{/plugins/dropzone/min/dropzone.min.js}"></script>
    <!-- Filterizr-->
    <script th:src="@{/plugins/filterizr/jquery.filterizr.min.js}"></script>
    <!-- Ekko Lightbox -->
    <script th:src="@{/plugins/ekko-lightbox/ekko-lightbox.min.js}"></script>
    <!-- Page specific script -->
    <script>
      $(function () {
        $("#example1").DataTable({
          "responsive": true, "lengthChange": false, "autoWidth": false,
          "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"]
        }).buttons().container().appendTo('#example1_wrapper .col-md-6:eq(0)');
        $('#example2').DataTable({
          "paging": true,
          "lengthChange": false,
          "searching": false,
          "ordering": true,
          "info": true,
          "autoWidth": false,
          "responsive": true,
        });
      });
    </script>
  </div>

</body>

</html>