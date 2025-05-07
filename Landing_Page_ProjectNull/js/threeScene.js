// Three.js configuration for the space scene with planets

let scene, camera, renderer, planets = [];

function initThreeScene() {
    // Create scene
    scene = new THREE.Scene();
    
    // Create camera
    camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    camera.position.z = 30;
    
    // Create renderer
    renderer = new THREE.WebGLRenderer({ antialias: true, alpha: true });
    renderer.setSize(window.innerWidth, window.innerHeight);
    renderer.setPixelRatio(window.devicePixelRatio);
    renderer.setClearColor(0x050510, 1);
    document.getElementById('three-container').appendChild(renderer.domElement);
    
    // Add ambient light
    const ambientLight = new THREE.AmbientLight(0x333333);
    scene.add(ambientLight);
    
    // Add directional light
    const directionalLight = new THREE.DirectionalLight(0xffffff, 1);
    directionalLight.position.set(5, 3, 5);
    scene.add(directionalLight);
    
    // Create stars background
    createStarField();
    
    // Create planets
    createPlanets();
    
    // Handle window resize
    window.addEventListener('resize', onWindowResize);
    
    // Start animation loop
    animate();
}

function createStarField() {
    const starGeometry = new THREE.BufferGeometry();
    const starMaterial = new THREE.PointsMaterial({
        color: 0xffffff,
        size: 0.1,
        transparent: true,
        opacity: 0.8
    });
    
    const starVertices = [];
    for (let i = 0; i < 5000; i++) {
        const x = (Math.random() - 0.5) * 2000;
        const y = (Math.random() - 0.5) * 2000;
        const z = (Math.random() - 0.5) * 2000;
        starVertices.push(x, y, z);
    }
    
    starGeometry.setAttribute('position', new THREE.Float32BufferAttribute(starVertices, 3));
    const starField = new THREE.Points(starGeometry, starMaterial);
    scene.add(starField);
}

function createPlanets() {
    // Sun
    const sunGeometry = new THREE.SphereGeometry(5, 32, 32);
    const sunMaterial = new THREE.MeshPhongMaterial({
        color: 0xfdb813,
        emissive: 0xfdb813,
        emissiveIntensity: 0.5,
        specular: 0xffffff,
        shininess: 10
    });
    const sun = new THREE.Mesh(sunGeometry, sunMaterial);
    scene.add(sun);
    
    // Create orbiting planets
    const planetCount = 5;
    
    for (let i = 0; i < planetCount; i++) {
        const size = Math.random() * 1.5 + 0.5;
        const distance = 10 + i * 5;
        const speed = 0.001 + Math.random() * 0.002;
        
        const geometry = new THREE.SphereGeometry(size, 32, 32);
        const material = new THREE.MeshPhongMaterial({
            color: new THREE.Color(Math.random(), Math.random(), Math.random()),
            specular: 0x111111,
            shininess: 5
        });
        
        const planet = new THREE.Mesh(geometry, material);
        
        // Position planet in orbit
        const angle = Math.random() * Math.PI * 2;
        planet.position.x = Math.cos(angle) * distance;
        planet.position.z = Math.sin(angle) * distance;
        
        // Add ring for some planets
        if (Math.random() > 0.7) {
            const ringGeometry = new THREE.RingGeometry(size * 1.3, size * 1.7, 32);
            const ringMaterial = new THREE.MeshPhongMaterial({
                color: 0xcccccc,
                side: THREE.DoubleSide
            });
            const ring = new THREE.Mesh(ringGeometry, ringMaterial);
            ring.rotation.x = Math.PI / 2;
            planet.add(ring);
        }
        
        scene.add(planet);
        planets.push({ mesh: planet, distance, speed, angle });
    }
}

function animate() {
    requestAnimationFrame(animate);
    
    // Rotate planets
    planets.forEach(planet => {
        planet.angle += planet.speed;
        planet.mesh.position.x = Math.cos(planet.angle) * planet.distance;
        planet.mesh.position.z = Math.sin(planet.angle) * planet.distance;
        planet.mesh.rotation.y += 0.01;
    });
    
    // Slowly rotate camera for better view
    camera.position.x = Math.sin(Date.now() * 0.0001) * 30;
    camera.position.z = Math.cos(Date.now() * 0.0001) * 30;
    camera.lookAt(0, 0, 0);
    
    renderer.render(scene, camera);
}

function onWindowResize() {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
}

// Initialize the scene when the script loads
initThreeScene();