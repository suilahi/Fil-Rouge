import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token : string | null = localStorage.getItem("token");

  const isAuthEndpoint = req.url.includes('/api/auth/login');

  if (isAuthEndpoint) {
    return next(req);
  }
  const newreq = req.clone({
    setHeaders : {
      Authorization : `Bearer ${token}`
    }
  })
  console.log(newreq)
  return next(newreq);
};
